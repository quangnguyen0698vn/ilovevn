const MAX_FILE_SIZE  = 1024 * 1024;
const contextPath = document.querySelector("#contextPath").innerHTML;
$(document).ready(function() {
	$("#buttonCancel").on("click", function() {
		window.history.go(-1)	});
	
	$("#fileImage").change(function() {
		if (!checkFileSize(this)) {
			return;
		}
		
		showImageThumbnail(this);				
	});
});

function showImageThumbnail(fileInput) {
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result);
	};
	
	reader.readAsDataURL(file);
}

function checkFileSize(fileInput) {
	fileSize = fileInput.files[0].size;
	
	if (fileSize > MAX_FILE_SIZE) {
		fileInput.setCustomValidity("Dung lượng file hình phải nhỏ hơn " + MAX_FILE_SIZE + " bytes!");
		fileInput.reportValidity();
		
		return false;
	} else {
		fileInput.setCustomValidity("");
		
		return true;
	}	
}
