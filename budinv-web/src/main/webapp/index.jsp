<html>
<body>
<script src="mustache.js"></script>
<script src="jquery-1.7.2.min.js"></script>

<script id="tpl-ledger" type="text/html">
<ul>{{#entries}} {{#entry}} <li> {{amount}} </li> {{/entry}} {{/entries}}</ul>
</script>

<script>
$.getJSON('/budinv-web/services/ledger', function(data) {
    var template = $('#tpl-ledger').html();
    var html = Mustache.to_html(template, data);
    $('body').append(html);
});
</script>

</body>
</html>
