<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="container mb-5" style="max-width: 68rem">
    <div class="row m-2 d-flex flex-wrap align-items-center">
        <div class="col-md-8 col-12">
            <h1 class="text-success fs-3 mt-5">Nền tảng quyên góp từ thiện I love VN</h1>
            <p class="mt-4">
                I love VN là nền tảng giúp bạn dễ dàng chung tay quyên góp tiền cùng hàng triệu người, giúp đỡ các hoàn cảnh khó khăn trên khắp cả nước.
            </p>

            <div class="row">
                <div class="col-md-3 col-4">
                    <h4 class="align-middle">| <span class="align-middle number-of-projects"></span></h4>
                    <p>dự án đã và đang được quyên góp</p>
                </div>
                <div class="col-md-3 col-4">
                    <h4 class="align-middle">| <span class="align-middle sum-of-raised-amount">48</span><span class="align-bottom">+ triệu</span></h4>
                    <p>đồng được quyên góp</p>
                </div>
                <div class="col-md-3 col-4">
                    <h4 class="align-middle">| <span class="align-middle number-of-donations"></span><span class="align-bottom">+ </span></h4>
                    <p>lượt quyên góp</p>
                </div>
                <div class="col-md-3 d-none d-md-block">
                </div>
            </div>


            <div class="mt-3">
                <a href="#projectSection" id="btnProjects" class="btn btn-success p-2 align-top">
                    Quyên góp
                </a>

                <a href="#charitySection" id="btnCharities" class="ms-4 btn btn-outline-success p-2 align-top">
                    Các đối tác đồng hành
                </a>
            </div>


        </div>
        <div class="col-md-4 col-12 d-none d-md-block align-middle">
            <img class="img-fluid" src="${pageContext.request.contextPath}/images/ilovevn-cover.jpeg" />
        </div>
    </div>
</section>