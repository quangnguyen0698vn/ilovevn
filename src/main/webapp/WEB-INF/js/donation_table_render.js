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

        debug('request at line 35', request);
        debug('drawCallback at line 36 ', drawCallback);
        debug('settings at line 37', settings);
        debug('requestStart at line 38', requestStart);
        debug('drawStart at line 39', drawStart);
        debug('requestLength at line 40', requestLength);
        debug('requestEnd at line 41', requestEnd);

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
                    pageNum: Math.floor((request.length + request.start) / request.length),
                    sortField: request.columns[request.order[0].column].data,
                    sortDir: request.order[0].dir,
                    keyword: request.search.value
                },
                dataType: 'json',
                success: function(json) {
                    json = {
                        recordsTotal: json.totalDonations,
                        recordsFiltered: json.totalDonations,
                        data: json.data,
                        draw: request.draw,
                    }
                    debug('json at line 100: ', json);

                    cacheLastJson = $.extend(true, {}, json);
                    debug('cacheLastJson at line 103: ', cacheLastJson);

                    if (cacheLower != drawStart) {
                        json.data.splice(0, drawStart - cacheLower);
                        debug('json at line 107: ', json);
                    }
                    if (requestLength >= -1) {
                        json.data.splice(requestLength, json.data.length);
                        debug('json at line 111: ', json);
                    }

                    debug('json at line 114: ', json);
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
    $("#donationTable").DataTable({
        responsive: true,
        process: true,
        search: {
            return: true, //false ~ instant search ~ more ajaxs than true
        },
        serverSide: true,
        order: [[1, 'desc']],

        lengthMenu: [
            [5, 10, 20, -1],
            [5, 10, 20, 'All'],
        ],
        ajax:
            $.fn.dataTable.pipeline({
                url: 'http://localhost:8585/ilovevn/admin/api/donations',
                pages: 5, // number of pages to cache
            }),
        columns: [

            {name: 'Mã số', data: 'id'},
            {name: 'Ngày điền form:', data: 'createdTime'},
            {name: 'Cập nhật lần cuối:', data: 'updatedTime'},
            {name: 'Họ và tên', data: 'fullName'},
            {name: 'Số tiền', data: 'amount'},
            {name: 'Mã số giao dịch', data: 'transRefNo'},
            {name: 'Lời nhắn', data: 'message'},
            {name: 'Trạng thái', data: 'state'},
            {name: 'Chuyển trạng thái', data: 'state'},
            {name: 'Mã số dự án', data: 'project_id'},
            {name: 'Mã số người dùng', data: 'user_id'},
        ],

        columnDefs: [
            {
                targets: 1,
                render: function ( data, type, row, meta ) {
                    const text = $.format.date(data, 'dd/MM/yyyy HH:mm:ss');
                    return text;
                }
            },
            {
                targets: 2,
                render: function ( data, type, row, meta ) {
                    const text = $.format.date(data, 'dd/MM/yyyy HH:mm:ss');
                    return text;
                }
            },
            {
                targets: 4,
                render: function ( data, type, row, meta ) {
                    const text = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(data);
                    return text;
                }
            },

            {
                targets: 9,
                "render": function ( data, type, row, meta ) {
                    const text =
`
<a href="${prefix+'/view/project?id='+data}" target="_blank">Dự án số ${data}</a>
`;
                    return text;
                }
            },
            {
                targets: 10,
                "render": function ( data, type, row, meta ) {
                    const checkNull = data + '';
                    const text = checkNull === 'null' ? 'Guest' :
                        `
<a href="${prefix+'/admin/users/edit/'+data}?readonly=true" target="_blank">Người dùng số ${data}</a>
`;
                    return text;
                }
            },
            {
                targets: 7,
                render: function ( data, type, row, meta ) {
                    let className = 'tag accepted-tag';
                    if (data == 'REJECTED') className = 'tag rejected-tag';
                    if (data == 'PENDING') className = 'tag pending-tag';
                    return `<span class="${className}">${data}</span>`;
                }
            },
            {
                targets: 8,
                render: function ( data, type, row, meta ) {
                    let accept = `<a href="${prefix+'/admin/donations/'+row['id']+'/state/ACCEPTED'}" class="text-success" title="Chấp nhận giao dịch này"><i class="fa fa-check-circle fa-2x"></i></a>`;
                    let spacing = `&nbsp;&nbsp;`;
                    let reject = `<a href="${prefix+'/admin/donations/'+row['id']+'/state/REJECTED'}" class="text-danger" title="Từ chối giao dịch này"><i class="fa-solid fa-circle-xmark fa-2x"></i></a>`;
                    if (data === 'ACCEPTED') return ``;
                    if (data === 'PENDING') return  accept + spacing + reject;
                    if (data == 'REJECTED') return  accept;
                }
            },
        ],

      language: {
        url: 'https://cdn.datatables.net/plug-ins/1.12.1/i18n/vi.json'
      }
    });
  });
