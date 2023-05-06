<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Test UI Tool</title>
		<link rel="stylesheet" href="main.css">
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

		<script>
			const apiDetails = JSON.parse('[{'
				+ '"name": "Option I", '
				+ '"info": "This option is the first example of what may be achieved."'
			+ '}, {'
				+ '"name": "Option II", '
				+ '"info": "This option is the second example of what may be achieved."'
			+ '}, {'
				+ '"name": "Option III", '
				+ '"info": "This option is the third example of what may be achieved."'
			+ '}, {'
				+ '"name": "Option IV", '
				+ '"info": "This option is the fouth example of what may be achieved."'
			+ '}]');

			function init() {
				var index = -1;
				const menu = document.getElementById("menuItems");
				const cards = document.getElementById("cards");

				menu.appendChild(createMenuItem("Home", "home", true));
				for (var i = 0; i < apiDetails.length; i++) {
					menu.appendChild(createMenuItem(apiDetails[i].name, "option" + (i+1), i == index));
					cards.appendChild(buildCard(i));
 	           	}
            	menu.appendChild(document.createElement('hr'));
				menu.appendChild(createMenuItem("Login", "login", i == apiDetails.length));
				
				document.title = "Test UI Tool";

				const title = document.getElementById("contentTitle");
				var p = document.createElement('p');
				
				p.innerHTML = "Home";
				title.appendChild(p);
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
			
			function createMenuItem(name, pageHref, disabled = false) {
				var p = document.createElement('p');

				if (disabled) {
					// <p class="disabled">Option 1</p>
					p.innerHTML = name;
					p.className = "disabled";
				} else {
					// <p><a href="home.html">Home</a></p>
					var a = document.createElement('a');

					a.href = pageHref;
					a.innerHTML = name;
					p.appendChild(a);
				}

				return p;
			}
		</script>
	</body>
</html>