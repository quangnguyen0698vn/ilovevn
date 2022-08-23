let countAjaxProj = { value: 1 };
let loadedAjaxProj = { value: 0 };
let countAjaxChar = { value: 1 };
let loadedAjaxChar = { value: 0 };
const contextPath =$("#contextPath").text();
// console.log(contextPath);
// console.dir(window.location);

// const url = window.location.origin + contextPath + '/api/projects/page/' + count;
// console.log(url);

let project_api_url = '/api/projects/page/';
let charity_api_url = '/api/charities/page/';

function addMoreProjects(projects, count, loaded) {
    console.dir(projects);
    let html = '';
    projects.forEach(function(proj) {
        loaded.value++;
        let raisedAmount = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(proj.raisedAmount);
        let targetAmount = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(proj.targetAmount);
        html +=
            `
<div class="col-lg-4 col-md-6 col-12">
    <div class="card h-100 border-info">
        <img class="card-img-top" src ="${contextPath}${proj.imagePath}" alt="${proj.name}" />
        <div class="card-body h-100>
          <h4 class="card-title">
                <a href="${contextPath}/view/project?id=${proj.id}" class="link-stretched text-decoration-none text-success">${proj.name}
                </a>
          </h4>
        </div>
        <div class="card-footer">
<!--            <img src="" class="charity-img" alt=""/>-->
            <div class="mb-3 d-flex flex-nowrap align-items-center">
                <div class="flex-shrink-0">
                   <div class="overflow-hidden rounded-full">
                        <div class="position-relative" style="width: 2.75rem; height: 2.75rem">
                            <span class="span-charity me-2">
                            <img alt="${proj.charity.name}" src="${contextPath}${proj.charity.charityLogoPath}" 
                            class="img-charity">
                        </div>
                   </div>
                </div>
                    
                <div class="flex-grow-1 text-xs">${proj.charity.name}</div>
                <div class="flex-shrink-0 text-xs"><span class="">Còn ${proj.numberOfDaysLeft} Ngày</span></div>  
            </div>  
            
            <div class="mb-3 d-flex align-items-end">
                <strong>${raisedAmount}</strong>
                <span>/${targetAmount}</span>
            </div>       
            
            <div class="mb-3 progress">
                <div class="progress-bar" role="progressbar" 
                style="width: ${proj.raisedPercentage*100}%;"
                aria-label="Tiến độ quyên góp" aria-valuenow="${proj.raisedPercentage*100}" aria-valuemin="0" aria-valuemax="100"></div>
            </div>           
            
            <div class="mb-3 d-flex flex-nowrap justify-content-between align-items-center">
                <div class="flex-grow-1">
                    <div class="text-xs"">Lượt quyên góp</div>
                    <div class="text-xs"">${proj.numberOfDonations}</div>    
                </div>
                <div class="flex-grow-1 text-xs">
                    <div class="text-xs"">Đạt được</div>
                    <div class="text-xs"">${proj.raisedPercentage*100}%</div>    
                </div>
                <div class="flex-grow-2 align-content-right"><a href="#" class="btn btn-outline-success text-xs">Quyên góp</a></div> 
            </div>      
        </div>
    </div>
</div>
`;
    });
    // console.dir(html);

    $.ajax({
        url: window.location.origin + contextPath + '/api/projects/size',
        success: function(number) {
            console.dir(number);
            if (loaded.value == number) {
                $("#loadMoreProjectBtn").remove();
            }
        }
    });

    $(".project-container").append(html);
    count.value++;
}

function addMoreCharities(charities, count, loaded) {
    console.dir(charities);
    loaded.value += charities.length;
    count.value++;
    let html = ``;

    charities.forEach(function(charity) {
       html+=
`
<div class="col-lg-4 col-md-6 col-12">
    <div class="card h-100 border-info">
        <div class="row align-items-center m-auto">
            <div class="col-4">
                <img class="card-img-top mx-2" src ="${contextPath}${charity.charityLogoPath}" alt="${charity.name}" />      
            </div>
            <div class="col-8 card-body">
                <h5 class="card-title mx-2 fs-6">
                    ${charity.name}
                </h5>
                <p class="text-xs mx-2">${charity.shortDescription}</p>
                <p>
                    <a class="text-xs mx-2 link-stretched text-decoration-none ${charity.havingDetails == false ? 'd-none' : ''}" href="${contextPath}/view/charities?=${charity.id}">Xem chi tiết >></a>
                </p>
            </div>
        </div>
    </div>
</div>
`;

    });

    $.ajax({
        url: window.location.origin + contextPath + '/api/charities/size',
        success: function(number) {
            console.dir(number);
            if (loaded.value == number) {
                $("#loadMoreCharityBtn").remove();
            }
        }
    });

    $(".charity-container").append(html);
}

function ajaxCall(api_url, count, loaded, name) {
    $.ajax({
        url: window.location.origin + contextPath + api_url + count.value,
        success: function(data) {
            if (name === 'projects')
                addMoreProjects(data, count, loaded);
            else
                addMoreCharities(data, count, loaded);
        }
    });
}

ajaxCall(project_api_url, countAjaxProj, loadedAjaxProj, 'projects');
ajaxCall(charity_api_url, countAjaxChar, loadedAjaxChar, 'charities');

$(document).ready(function() {
    $("#loadMoreProjectBtn").click(function() {
        ajaxCall(project_api_url, countAjaxProj, loadedAjaxProj, 'projects');
    });
    $("#loadMoreCharityBtn").click(function () {
        ajaxCall(charity_api_url, countAjaxChar, loadedAjaxChar, 'charities');
    })
});

// Smooth scrolling animation

const allLinks = document.querySelectorAll("a:link");

allLinks.forEach(function (link) {
    link.addEventListener("click", function (e) {
        const href = link.getAttribute("href");

        if (!href.startsWith("#")) return;

        e.preventDefault();

        // Scroll back to top
        if (href === "#")
            window.scrollTo({
                top: 0,
                behavior: "smooth",
            });

        // Scroll to other links
        if (href !== "#" && href.startsWith("#")) {
            const sectionEl = document.querySelector(href);
            sectionEl.scrollIntoView({ behavior: "smooth" });
        }


    });
});

//