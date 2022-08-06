<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>I ❤ VN</title>
    <!-- Bootstrap and Fontawesome -->
    <jsp:include page='../common/cssFramework.jsp'/>
    <!-- My Own style -->
    <link rel="stylesheet" href="<c:url value="/css/style.css" context="${pageContext.request.contextPath}"></c:url>" />
</head>

<body class="d-flex flex-column min-vh-100">
<%-- Navbar--%>
<jsp:include page="header.jsp"/>
    <span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>
    <section class="container">
        <h2 class="mb-5">
            <c:out value="${pageTitle}"></c:out>
        </h2>
        <form method="post" action="save" enctype="multipart/form-data">
            <!-- Id -->
            <c:if test="${project.id != null}">
                <input hidden type="number" name="id" value="${project.id}"/>
            </c:if>
            <!-- Project name -->
            <div class="form-group row mb-4">
                <label class="col-lg-2 col-form-label" for="name">Tên dự án:</label>
                <div class="col-lg-10">
                    <input type="text" class="form-control" id="name" name="name" value="${project.name}" />
                </div>
            </div>

            <!-- Tổ chức đứng ra thực hiện dự án -->
            <div class="form-group row mb-4">
                <label class="col-lg-2 col-form-label" for="name">Tên Tổ chức kêu gọi quyên góp:</label>
                <div class="col-lg-10">
                    <select class="form-control" name="charityId" id="dropdownCharity">
                        <option value="0">Xin chọn tổ chức kêu gọi từ thiện</option>
                        <c:forEach items="${charities}" var="charity" varStatus="status">
                            <c:if test="${charity.id == project.charity.id}">
                                <option selected value="<c:out value="${charity.id}"></c:out>" >
                                    <c:out value="${charity.name}"></c:out>
                                </option>
                            </c:if>
                            <c:if test="${charity.id != project.charity.id}">
                                <option value="<c:out value="${charity.id}"></c:out>" >
                                    <c:out value="${charity.name}"></c:out>
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- Project target value and started, expired date -->
            <div class="form-group row mb-4">
                <label class="col-lg-2 col-form-label" for="targetAmount"
                >Số tiền dự kiến:</label
                >
                <div class="col-lg-2">
                    <input
                            type="number"
                            class="form-control"
                            id="targetAmount"
                            name="targetAmount"
                            value="<c:out value="${project.targetAmount}"></c:out>"
                    />
                </div>

                <label class="col-lg-2 col-form-label" for="expiredDate"
                >Ngày bắt đầu:</label
                >
                <div class="col-lg-2">
                    <input
                            type="date"
                            class="form-control"
                            id="startedDate"
                            name="startedDate"
                            value="<c:out value="${project.startedDate}"></c:out>"
                    />
                </div>

                <label class="col-lg-2 col-form-label" for="expiredDate"
                >Ngày hết hạn:</label
                >
                <div class="col-lg-2">
                    <input
                            type="date"
                            class="form-control"
                            id="expiredDate"
                            name="expiredDate"
                            value="<c:out value="${project.expiredDate}"></c:out>"
                    />
                </div>
            </div>

            <!-- Project Short Description -->
            <div class="form-group mb-4" id = "divShortDescription">
                <label class="col-lg-2 col-form-label mb-2" for="shortDescription"
                >Mô tả ngắn:</label
                >
                <textarea
                        name="shortDescription"
                        id="shortDescription"
                        class="form-control"
                >
                    <c:out value="${project.shortDescription}" escapeXml="false"></c:out>
                </textarea>
            </div>
            <!-- Project Full Description -->
            <div class="form-group mb-4" id = "divFullDescription">
                <label class="col-lg-2 col-form-label mb-2" for="fullDescription"
                >Bài viết đầy đủ:</label
                >
                <textarea
                        name="fullDescription"
                        id="fullDescription"
                        class="form-control"
                >
                    <c:out value="${project.fullDescription}" escapeXml="false"></c:out>
                </textarea>
            </div>

            <!-- Project Image -->

            <div class="row" id="divProjectImages">
                <!-- Hiển thị hình ảnh chính của dự án -->
                <div class="col border m-3 p-2">
                    <div>
                        <label>Hình ảnh chính của dự án: <c:out value="${project.mainImage != null ? project.mainImage : ''}"></c:out> </label>
                        <a
                                title="Xóa hình ảnh này" style="visibility: hidden;"
                                data-name="linkRemoveProjectImage"
                                href="#"
                        >
                            <i class="btn fas fa-times-circle fa-2x icon-dark float-right"
></i>
                        </a>
                    </div>
                    <div class="m-2">
                        <c:if test="${project.mainImage == null}">
                            <img id="thumbnail" alt="Hình ảnh chính của dự án" class="img-fluid" src="<c:url value="/images/image-thumbnail.png" context="${pageContext.request.contextPath}"></c:url>">
                        </c:if>


                        <c:if test="${project.mainImage != null}">
<%--                            <c:url value="${project.getImagePath()}" context="${pageContext.request.contextPath}"></c:url>--%>
                            <img id="thumbnail" alt="Hình ảnh chính của dự án" class="img-fluid" src="<c:url value="${project.getImagePath()}" context="${pageContext.request.contextPath}"></c:url>">
                        </c:if>
                    </div>
                    <div>
                        <c:if test="${project.mainImage == null}">
                            <input type="file" id="fileImage" name="fileImage" accept="image/png, image/jpeg" required>
                        </c:if>
                        <c:if test="${project.mainImage != null}">
                            <input type="file" id="fileImage" name="fileImage" accept="image/png, image/jpeg">
                            <input type="hidden" id="mainImage" name="mainImage" value="<c:out value="${project.mainImage}"></c:out>"/>
                        </c:if>
                    </div>
                </div>

                <!-- Hiển thị các hình ảnh minh họa của dự án -->
                <c:forEach items="${project.images}" var="image" varStatus="status">
                    <div class="col border m-3 p-2" id="divProjectImage${status.index}">
                        <div id="projectImageHeader${status.index}">
                            <label>Hình số #${status.index+1}: <c:out value="${image.fileName}"></c:out></label>
                            <a
                               class="btn fas fa-times-circle fa-2x icon-dark float-right"
                               title="Xóa hình ảnh này"
                               data-name="linkRemoveProjectImage"
                               >
                            </a>
                        </div>
                        <div class="m-2">
                            <img id="projectImageThumbnail${status.index}" alt="Hình số #${status.index+1}" class="img-fluid"
                                 src="<c:url value="${image.getImagePath()}" context="${pageContext.request.contextPath}"></c:url>"
                            />
                        </div>
                        <div>
                            <input type="file" name="projectImage" accept="image/png, image/jpeg">
                        </div>

                        <input type="hidden" name="imageIDs" id="imageId${status.index}"
                               value="${image.id}" />
                        <input type="hidden" name="imageNames" id="imageName${status.index}"
                               value="${image.fileName}" />
                    </div>
                </c:forEach>

                <div class="col border m-3 p-2" id="divProjectImage${status.index}">
                    <div id="projectImageHeader${numberOfExistingProjectImages}">
                        <label>Hình số #${numberOfExistingProjectImages+1}:</label>
                    </div>
                    <div class="m-2">
                        <img id="projectImageThumbnail${numberOfExistingProjectImages}"
                             alt="Hình số #${numberOfExistingProjectImages+1}"
                             class="img-fluid"
                             src="<c:url value="/images/image-thumbnail.png" context="${pageContext.request.contextPath}"></c:url>"
                        />
                    </div>
                    <div>
                        <input type="file" name="projectImage" accept="image/png, image/jpeg">
                    </div>
                </div>

            </div>


            <div class="text-center">
                <input type="submit" value="Lưu dự án" class="btn btn-primary m-3">
                <input type="button" value="Hủy bỏ" class="btn btn-secondary" id="buttonCancel">
            </div>
        </form>
    </section>

<%--Footer--%>
<jsp:include page="footer.jsp"/>

<%-- js framework --%>
<jsp:include page="../common/jsFramework.jsp"/>

<!-- JQuery -->
<script
        src="http://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"
></script>

<!-- CKeditor -->
<script src="https://cdn.ckeditor.com/ckeditor5/35.0.1/classic/ckeditor.js"></script>


<script>
    ClassicEditor.create(document.querySelector("#shortDescription"));
    ClassicEditor.create(document.querySelector("#fullDescription"));
</script>

<!-- my own script -->

<script src=<c:url value="/js/common.js" context="${pageContext.request.contextPath}"></c:url>></script>
<script src=<c:url value="/js/project_form_image.js" context="${pageContext.request.contextPath}"></c:url>></script>
</body>
</html>

