$(document).ready(function() {
	$("#add-computer-form").submit(function(e) {
		var introduced = $("#introduced").val();
		var discontinued = $("#discontinued").val();

		$(".error").remove();
		introduced = Date.parse(introduced);
		alert(introduced);
		if(isNaN(introduced)){
			$("#introduced").after("<p class=\"error\">Introduced date format : yyyy-MM-dd</p>");
			e.preventDefault();
		}
		else{
			if(isNaN(Date.parse(discontinued))){
				$("#discontinued").after("<p class=\"error\">Discontinued date format : yyyy-MM-dd</p>");
				e.preventDefault();
			}
			else{
				if (introduced > discontinued) {
					$("#discontinued").after("<p class=\"error\">Introduced date can't be upper than discontinued date</p>");
					e.preventDefault();
				}
			}
		}
	});
});