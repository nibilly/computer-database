$(document).ready(function() {
	$("#computer-form").validate(
		{
		rules: {
			"name": {
				required: true
			},
			"introduced": {
				date: true
			},
			"discontinued": {
				date: true
			}
		}
			
	});
});