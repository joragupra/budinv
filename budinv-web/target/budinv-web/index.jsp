<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<link href="./css/bootstrap-responsive.css" rel="stylesheet">

<script src="./js/mustache.js"></script>
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/jquery.dataTables.min.js"></script>
<script src="./js/controller.js" type="text/javascript"></script>



<div class="container-fluid">
  <div class="row-fluid">
    <div class="span2">
      <!--Sidebar content-->
SideBar
    </div>
    <div class="span10">
      <!--Body content-->
Body
	<table id="ledgers">
    	<thead>	
			<tr>
        	    <th>Incurred Date</th>
        	    <th>Entry type</th>
        	    <th>Amount</th>
        	</tr>
    	</thead>
 		<tbody>
		{{#entries}}
			{{#entry}}
			<tr> <td>{{incurredDate}}</td> <td>{{entryType}}</td> <td>{{amount}}</td> </tr>
			{{/entry}}
		{{/entries}}
    	</tbody>
	</table>
    </div>
  </div>
</div>

</body>
</html>
