function load(serviceURL){
	$(document).ready(function() {
		$.getJSON(serviceURL, function(data) {
		    var template = $('#template').html();
		    var html = Mustache.to_html(template, data);
		    $('body').html(html);
		});
	});
}

function load(serviceURL, table){
	$(document).ready(function() {
		$.getJSON(serviceURL, function(data) {
		    var template = $('#template').html();
		    var html = Mustache.to_html(template, data);
		    $('body').html(html);
		});
		$(document).ajaxStop(function(){
			$('#' + table).dataTable();
		});
	});
}