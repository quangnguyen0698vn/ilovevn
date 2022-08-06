var projectImagesCount = 0;

$(document).ready(function() {	
	$("input[name='projectImage']").each(function(index) {
		projectImagesCount++;
		
		$(this).change(function() {
			if (!checkFileSize(this)) {
				return;
			}			
      // console.log("Dung lượng OK");
			showProjectImageThumbnail(this, index);
		});
	});
	
	$("svg[data-name='linkRemoveProjectImage']").each(function(index) {
		$(this).click(function() {
			removeProjectImage(index);
		});
	});
	
});

function showProjectImageThumbnail(fileInput, index) {
	var file = fileInput.files[0];
	
	fileName = file.name;
	// console.log(fileName);
	
	imageNameHiddenField = $("#imageName" + index);
	if (imageNameHiddenField.length) {
		imageNameHiddenField.val(fileName);
	}
		
	
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#projectImageThumbnail" + index).attr("src", e.target.result);
	};
	
	reader.readAsDataURL(file);	
	
	if (index >= projectImagesCount - 1) {
		addNextProjectImageSection(index + 1);		
	}
}

function addNextProjectImageSection(index) {
	htmlProjectImage = `
		<div class="col border m-3 p-2" id="divProjectImage${index}">
			<div id="projectImageHeader${index}"><label>Hình số #${index + 1}:</label></div>
			<div class="m-2">
				<img id="projectImageThumbnail${index}" alt="Hình số #${index + 1} preview" class="img-fluid"
					src="${contextPath}/images/image-thumbnail.png"/>
			</div>
			<div>
				<input type="file" name="projectImage"
					onchange="showProjectImageThumbnail(this, ${index})" 
					accept="image/png, image/jpeg" />
			</div>
		</div>	
	`;
	
	htmlLinkRemove = `
		<a class="btn fas fa-times-circle fa-2x icon-dark float-right"
			href="#" onclick="javascript:removeProjectImage(${index - 1})" 
			title="Xóa hình ảnh này">
    </a>
	`;
	
	$("#divProjectImages").append(htmlProjectImage);
	
	$("#projectImageHeader" + (index - 1)).append(htmlLinkRemove);
	
	projectImagesCount++;
}

function removeProjectImage(index) {
  // console.log('this function is called');
	$("#divProjectImage" + index).remove();
}