function showTable(name) {
	$("." + name).show();
}
$(function() {
	$('.are-you-sure').click(function() {
		var r = confirm("Are you sure?");
		if (r == false) {
			return false;
		}
	});
})
