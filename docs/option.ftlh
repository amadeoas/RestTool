<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Transactions Flow UI</title>
		<link rel="shortcut icon" href="#" />
		<link rel="stylesheet" href="main.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	</head>
	<body onload="javascript:init()">
    	<div name="main" id="main" class="section">
        	<header id="contentHead" class="header">
        		<div id="contentTitle">${uiName}</div>
        		<div class="select" style="min-width: 500px">
        			<form name="filesForm" id="filesForm" enctype="multipart/form-data" method="POST" action="/uploadFiles" onsubmit="readFiles()">
        				<div>
	        				<label for="fileInput">Directory: </label>
  							<input onchange="changeInput()" name="fileInput" id="fileInput" type="file" webkitdirectory directory multiple />
  							<button name="btnFiles" id="btnFiles" class="styled buttonDisabled" type="submit" disabled>Upload</button>
  						</div>
  						<div>
							<label for="fromDate">From: </label><input name="fromDate" id="fromDate" type="datetime-local" step="1" /> <label for="toDate">To: </label><input name="toDate" id="toDate" type="datetime-local" step="1" />
						</div>
					</form>
        		</div>
        	</header>
        	<article id="contentBody" class="main">
				<div name="flowView" id="flowView">
					<div name="showView" id="showView">
					</div>
					<div name="tooltip" id="tooltip" hidden>
						...
					</div>
				</div>
       			<div name="errorView" id="errorView" hidden>
       			</div>
           	</article>
        	<footer class="footer">
	        	<hr style="border: 1;color: lightgray;" />
	        	<div id="footer">
	    		</div>
        	</footer>
    	</div>

		<script type="text/javascript">
			<#include "page.js" />
		</script>
	</body>
</html>