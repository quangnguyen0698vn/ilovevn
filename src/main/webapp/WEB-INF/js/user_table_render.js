const prefix = $("#contextPath")[0].outerText;
// const prefix2 = $("#contextPath2")[0].outerText;

let debug = function(name, value) {
    console.log(name);
    console.dir(value);
}


$.fn.dataTable.pipeline = function (opts) {
    var conf = $.extend(
        {
            data: null, // function or object with parameters to send to the server
            // matching how `ajax.data` works in DataTables
            method: 'GET', // Ajax HTTP method
        },
        opts
    );

    // debug('conf', conf);

    // Private variables for storing the cache
    var cacheLower = -1;
    var cacheUpper = null;
    var cacheLastRequest = null;
    var cacheLastJson = null;

    return function (request, drawCallback, settings) {
        var ajax = false;
        var requestStart = request.start;
        var drawStart = request.start;
        var requestLength = request.length;
        var requestEnd = requestStart + requestLength;

        // debug('request at line 109', request);
        // // debug('drawCallback', drawCallback);
        // // debug('settings', settings);
        // debug('requestStart', requestStart);
        // debug('drawStart', drawStart);
        // debug('requestLength', requestLength);
        // debug('requestEnd', requestEnd);

        if (settings.clearCache) {
            // API requested that the cache be cleared
            ajax = true;
            settings.clearCache = false;
        } else if (cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper) {
            // outside cached data - need to make a request
            ajax = true;
        } else if (
            JSON.stringify(request.order) !== JSON.stringify(cacheLastRequest.order) ||
            JSON.stringify(request.columns) !== JSON.stringify(cacheLastRequest.columns) ||
            JSON.stringify(request.search) !== JSON.stringify(cacheLastRequest.search)
        ) {
            // properties changed (ordering, columns, searching)
            ajax = true;
        }

        // Store the request for checking next time around
        cacheLastRequest = $.extend(true, {}, request);

        if (ajax) {
            // Need data from the server
            if (requestStart < cacheLower) {
                requestStart = requestStart - requestLength * (conf.pages - 1);

                if (requestStart < 0) {
                    requestStart = 0;
                }
            }

            cacheLower = requestStart;
            cacheUpper = requestStart + requestLength * conf.pages;

            request.start = requestStart;
            request.length = requestLength * conf.pages;

            $.extend(request, conf.data);
            // debug('request at line 146: ', request);

            // Quang's ajax call:
            return $.ajax({
                type: conf.method,
                url: conf.url,
                data: {
                    pageSize: request.length,
                    pageNum: (request.length + request.start) / request.length,
                    sortField: request.columns[request.order[0].column].data,
                    sortDir: request.order[0].dir,
                    keyword: request.search.value
                },
                dataType: 'json',
                success: function(json) {
                    json = {
                        recordsTotal: json.totalUsers,
                        recordsFiltered: json.totalUsers,
                        data: json.data,
                        draw: request.draw,
                    }
                    // debug('json at line 168: ', json);

                    cacheLastJson = $.extend(true, {}, json);
                    // debug('cacheLastJson at line 171: ', cacheLastJson);

                    if (cacheLower != drawStart) {
                        json.data.splice(0, drawStart - cacheLower);
                        // debug('json at line 175: ', json);
                    }
                    if (requestLength >= -1) {
                        json.data.splice(requestLength, json.data.length);
                        // debug('json at line 179: ', json);
                    }

                    // debug('json at line 188: ', json);
                    drawCallback(json);
                }
            });
        } else {
            // do not need to call ajax
            json = $.extend(true, {}, cacheLastJson);
            json.draw = request.draw; // Update the echo for each response
            json.data.splice(0, requestStart - cacheLower);
            json.data.splice(requestLength, json.data.length);

            drawCallback(json);
        }
    };
};
// Register an API method that will empty the pipelined data, forcing an Ajax
// fetch on the next draw (i.e. `table.clearPipeline().draw()`)
$.fn.dataTable.Api.register('clearPipeline()', function () {
    return this.iterator('table', function (settings) {
        settings.clearCache = true;
    });
});

$(document).ready( function() {
    $("#table_less_ajax_calls").DataTable({
        responsive: true,
        process: true,
        search: {
            return: true, //false ~ instant search ~ more ajaxs than true
        },
        serverSide: true,
        lengthMenu: [
            [3, 6, 9, -1],
            [3, 6, 9, 'All'],
        ],
        ajax:
            $.fn.dataTable.pipeline({
                url: 'http://localhost:8585/ilovevn/admin/api/users',
                pages: 5, // number of pages to cache
            }),
        columns: [

            // { title: 'Mã số'},
            {name: 'id', data: 'id'},
            // { title: 'Họ và tên'},
            {name: 'fullName', data: 'fullName'},
            // { title: 'Email'},
            {name: 'email', data: 'email'},
            // { title: 'Địa chỉ'},
            {name: 'address', data: 'address'},
            // { title: 'Số điện thoại'},
            {name: 'phoneNumber', data: 'phoneNumber'},
            // { title: 'Vai trò'},
            {name: 'role', data: 'role.name'},
            // { title: 'Avatar'},
            {name: 'avatar', data: 'profilePhotoPath'},
            // { title: 'Enabled'},
            {name: 'enabled', data: 'enabled'},
            {name: 'edit_delete', data: null},
        ],

        columnDefs: [
            {
                targets: 5,
                "render": function ( data, type, row, meta ) {
                    const text = data.substring(5);
                    return text;
                }
            },

            {
                targets: 6,
                "render": function ( data, type, row, meta ) {
                    const imgSource = prefix + data;
                    return '<img class="img-fluid" src="' + imgSource + '"/>';
                }
            },
            {
                targets: 7,
                "render": function ( data, type, row, meta ) {
                    // debug('data = ', data);
                    // debug('type = ', type);
                    // debug('row = ', row);
                    // debug('meta = ', meta);
                    // console.log(data + ' is a ' + typeof data);
                    const url = prefix + '/admin/users/' + row['id'] + '/enabled/' + !data;

                    if (data) {
                        return `<a href="` + url + `" class="text-success" title="Vô hiệu hóa tài khoản này"><i class="fa fa-check-circle fa-2x"/></a>`
                    } else {
                        return `<a href="` + url + `" class="text-muted" title="Kích hoạt tài khoản này"><i class="fa fa-circle fa-2x"/></a>`
                    }
                }
            },
            {
                targets: 8,
                "render": function ( data, type, row, meta ) {
                    // debug('data = ', data);
                    // debug('type = ', type);
                    // debug('row = ', row);
                    // debug('meta = ', meta);
                    // console.log(data + ' is a ' + typeof data);
                    const urlEdit = prefix + '/admin/users/edit/' + row['id'];
                    const urlDelete = prefix + '/admin/users/delete/' + row['id'];
                    let result =
                        `
                        <a href="${urlEdit}" 
                            data-user-id=${data.id} 
                            data-user-email=${data.email} 
                            data-user-full-name="${data.fullName}"
                            data-user-role=${data.role.name}
                            class="text-success" title="Chỉnh sửa tài khoản này">
                            <i class="fa fa-edit fa-2x" aria-hidden="true"></i>
                        </a>
                        <!--
                        <a href="${urlDelete}" 
                            data-user-id=${data.id} 
                            data-user-email=${data.email} 
                            data-user-full-name="${data.fullName}"
                            data-user-role=${data.role.name}
                            class="text-muted link-delete" title="Xóa tài khoản này">
                            <i class="fa fa-trash fa-2x" aria-hidden="true"></i>
                        </a>
                        -->
                      `;

            // debug('result at line 288: ', result);
            return result;

          }
        }
      ],

      language: {
        url: 'https://cdn.datatables.net/plug-ins/1.12.1/i18n/vi.json'
      }
    });

    $('#table_less_ajax_calls').on( 'draw.dt', function () {
        // console.log('table is loaded succesfully');
        $('.link-delete').on('click', function(e) {

            e.preventDefault();
            // debug('id: ', $(this).attr('data-user-id'));
            // debug('id: ', this.attributes['data-user-id'].value);
            const userRole = $(this).attr('data-user-role');
            // debug('userRole = ', userRole);
            if (userRole === 'ADMIN') {
                alert('Không thể xóa tài khoản ADMIN');
                return;
            }
            const userId = $(this).attr('data-user-id');
            const userFullName = $(this).attr('data-user-full-name');
            const userEmail = $(this).attr('data-user-email');

            const confirmed = confirm(`Bạn có thực sự muốn xóa người dùng ${userFullName} (Mã số: ${userId}, Email: ${userEmail} )`);
            if (confirmed) {
                window.location.href = $(this).attr("href");
            }
        });
    } );



  });

  // $(document).ready( function () {
  //   $('#table_many_ajax_calls').DataTable({
  //     processing: true,
  //     search: {
  //       return: true,
  //     },
  //     serverSide: true,
  //     lengthMenu: [
  //       [3, 6, 9, -1],
  //       [3, 6, 9, 'All'],
  //     ],
  //     ajax: function(data, callback) {
  //       // data.length = 3;
  //       // console.dir(data);
  //       // console.dir(data.columns[data.order[0].column].data);
  //       // console.dir(data.order[0].dir);
  //       $.get('http://localhost:8585/ilovevn/api/users', {
  //         pageSize: data.length,
  //         pageNum: (data.length + data.start) / data.length,
  //         sortField: data.columns[data.order[0].column].data,
  //         sortDir: data.order[0].dir,
  //         keyword: data.search.value
  //       } , function(res) {
  //         callback({
  //           recordsTotal: res.totalUsers,
  //           recordsFiltered: res.totalUsers,
  //           data: res.data,
  //           draw: data.draw,
  //         });
  //       });
  //     },
  //     columns: [
  //
  //       // { title: 'Mã số'},
  //       {data: 'id'},
  //       // { title: 'Họ và tên'},
  //       {data: 'fullName'},
  //       // { title: 'Email'},
  //       {data: 'email'},
  //       // { title: 'Địa chỉ'},
  //       {data: 'address'},
  //       // { title: 'Số điện thoại'},
  //       {data: 'phoneNumber'},
  //       // { title: 'Vai trò'},
  //       {data: 'role.name'},
  //       // { title: 'Avatar'},
  //       {data: 'profilePhotoPath'},
  //     ],
  //
  //     columnDefs: [ {
  //       targets: 6,
  //       "render": function ( data, type, row, meta ) {
  //         const imgSource = prefix + '/' + data;
  //         return '<img class="img-fluid" src="' + imgSource + '"/>';
  //       }
  //     } ],
  //   });
  // } );