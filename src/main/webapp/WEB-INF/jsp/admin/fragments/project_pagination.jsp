<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- PAGINATION -->
<c:set var="suffix" value="&charityId=${selectedCharityId}&keyword=${currentKeyword}&sortField=${sortField}&sortDir=${sortDir}"></c:set>
<nav aria-label="Search results pages" class="row m-5">
  <ul class="pagination justify-content-center">
    <c:if test="${currentPage == 1}">
      <li class="page-item disabled"><a href="#"
                                        class="page-link">Previous</a></li>
    </c:if>

    <c:if test="${currentPage > 1}">
      <li class="page-item"><a
              href="?pageNum=${currentPage-1}${suffix}"
              class="page-link">Previous</a></li>
    </c:if>

    <c:forEach var="i" begin="1" end="${totalPages}" step="1">
      <c:if test="${i == currentPage}">
        <li class="page-item active" aria-current="page"><a
                href="?pageNum=${i}${suffix}" class="page-link">
            ${i} <span class="visually-hidden">(current)</span>
        </a></li>
      </c:if>
      <c:if test="${i != currentPage}">
        <li class="page-item"><a
                href="?pageNum=${i}${suffix}" class="page-link">${i}</a></li>
      </c:if>
    </c:forEach>

    <c:if test="${currentPage == totalPages}">
      <li class="page-item disabled"><a href="#"
                                        class="page-link">Next</a></li>
    </c:if>

    <c:if test="${currentPage < totalPages}">
      <li class="page-item"><a
              href="?pageNum=${currentPage+1}${suffix}"
              class="page-link">Next</a></li>
    </c:if>
  </ul>
</nav>