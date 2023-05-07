<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>REST API UI Tool</title>
		<link rel="shortcut icon" href="#" />
		<link rel="stylesheet" href="main.css">
		<script type="text/javascript" src="pages.js"></script>
	</head>
	<body onload="init()">
    	<div id="main">
        	<div id="menu">
        		<div id="menuHead">
        			<div id="title">
	        			<p>Test Tool</p>
	        		</div>
	        		<div id="icon">
	        			<img title="Title" src="images/face-icon.png" height="20px" style="margin: 0;" />
	        		</div>
        		</div>
        		<div id="menuItems">
            	</div>
       		</div>
        	<div id="content">
        		<div id="contentHead">
        			<div id="contentTitle">
        			</div>
        		</div>
        		<div id="contentBody">
        			<div id="contentBHead">
	           			See below the APIs supported by this UI.
        			</div>
        			<div id="contentCards">
      		  			<ul id="cards" class="card-container">
  						</ul>
        			</div>
           		</div>
        	</div>
    	</div>

		<script type="text/javascript">
			function init() {
				const cards = document.getElementById("cards");

				buildMenu(-1);
				for (var i = 0; i < apiDetails.length; i++) {
					cards.appendChild(buildCard(i));
 	           	}
			}

			function buildCard(i) {
				const li = document.createElement('li');
				const card = document.createElement('div');
				var div;
				var button;

				card.className = "card";

				div = document.createElement('div');
				div.innerHTML = apiDetails[i].name;
				div.className = "cardHeader";
				card.appendChild(div);

				div = document.createElement('div');
				div.innerHTML = apiDetails[i].info;
				div.className = "container";
				card.appendChild(div);

				div = document.createElement('div');
				div.className = "cardFooter";
				button = document.createElement('button');
				button.type = "button";
				button.innerHTML = apiDetails[i].name;
				button.onclick = "window.location.href='option1'; return false;";
				div.appendChild(button);
				card.appendChild(div);

				li.appendChild(card);

				return li;
			}
		</script>
	</body>
</html>