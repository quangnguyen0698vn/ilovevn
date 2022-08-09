<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="full-details">
    <form id="hidden-form" action="<c:url value="/admin/projects/delete/" context="${pageContext.request.contextPath}"></c:url>">
        <table
                class="table table-bordered table-striped table-hover table-responsive-xl"
                data-toggle="table"
                data-show-columns="true"
        >
            <thead class="bg-dark text-white">
            <tr>
                <th class="checkbox-column"></th>
                <th class="hideable-column" data-sortable="true" >
                    Mã số
                </th>

                <th data-sortable="true" >
                    Tên dự án
                </th>

                <th class="image-column" data-width="200" >Hình ảnh</th>
                <th class="hideable-column" data-sortable="true" >Ngày bắt đầu</th>
                <th data-sortable="true" >Ngày kết thúc</th>
                <th data-sortable="true" >Số tiền quyên góp được</th>
                <th data-sortable="true" >Số tiền dự kiến quyên góp</th>
                <th data-sortable="true">Tiến độ</th>


                <th class="hideable-column" data-sortable="true">Tổ chức từ thiện</th>

                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${projects}" var="project">
                <tr>
                    <td class="checkbox-column">
                        <c:if test="${!project.isAlreadyStarted()}">
                            <input type="checkbox" name="id" value="<c:out value="${project.id}"></c:out>"/>
                        </c:if>
                    </td>
                    <td class="hideable-column"><c:out value="${project.id}"></c:out></td>

                    <td>
                        <c:out value="${project.name}"></c:out>
                    </td>



                    <td class="image-column">
                        <img
                                src="<c:url value="${project.getImagePath()}"></c:url>"
                                style="width: 500px"
                                class="img-fluid"
                        />
                    </td>

                    <td class="hideable-column"><fmt:formatDate value="${project.startedDate}" pattern="dd-MM-yyyy" /></td>
                    <td><fmt:formatDate value="${project.expiredDate}" pattern="dd-MM-yyyy" /></td>

                    <fmt:setLocale value="vn_VN"/>
                    <td><fmt:formatNumber value="${project.raisedAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></td>
                    <td><fmt:formatNumber value="${project.targetAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></td>
                    <td><fmt:formatNumber value="${project.raisedPercentage}" type="percent" minFractionDigits="2"/></td>
                    <td class="hideable-column"><c:out value="${project.charity.name}"></c:out></td>

                    <td>
                        <div class="mb-1">
                            <a
                                    class="link-edit"
                                    href="<c:url value="/admin/projects/edit/${project.id}" context="${pageContext.request.contextPath}"></c:url>"
                                    title="Sửa thông tin dự án"
                            >
                                <i class="fas fa-edit fa-2x icon-green"></i>
                            </a>
                        </div>

                        <c:if test="${!project.isAlreadyStarted()}">
                            <%--                                    <c:out value="${project.isAlreadyStarted()}"></c:out>--%>
                            <div>
                                <a class="link-delete"
                                   href="javascript:deleteProject(${project.id}, '${project.name}')"
                                   data-project-id = "<c:out value="${project.id}"></c:out>"
                                   title="Xóa dự án này">
                                    <i class="fas fa-trash fa-2x icon-dark"></i>
                                </a>
                            </div>
                        </c:if>




                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>

</div>