<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Test UI Tool - </title>
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
        			<div class="select">
        				<select name="env" id="env" onchange="updateEnv(this)">
        			        <option value="0">VOID</option>
        			    </select></p>
        			</div>
        		</div>
        		<div id="contentBody">
        			<form id="requestForm" action="return sendForm();">
        			<div id="hostView" style="display: flex;">
        				<input value="GET" type="text" id="method" style="width: 3.5em;" disabled /><span style="margin-top: .4em; margin-right: .1em">:</span>
        				<select name="host" id="host" onchange="updateHost(this)" style="border-bottom: 1px solid #c0c0c0; border-right: 1px solid #c0c0c0; background-color: #ffffff;">
        					<option title="Method: GET" value="0">VOID</option>
        				</select>
        				<button class="info" type="button" style="padding-left: 2px; background-color: #ffffff;"><img id="info" title="Information" src="images/info-icon.png" height="20px" /></button>
        				<button id="send" form="requestForm" type="button" class="button" onclick="sendForm()"><span>Send</span></button>
        				<button id="clear" form="requestForm" type="button" class="button" onclick="clearForm(this)"><span>Clear</span></button>
        			</div>
        			<div>
						<details id="requestDetails">
  							<summary>Request</summary>
  							<div id="request" class="tabset">
  								<!-- Tab Headers -->
  								<input type="radio" name="tabset" id="tab-request-header" aria-controls="requestHeader" onclick="checkTab(this)" checked>
  								<label id="lbl-request-header" for="tab-request-header">Headers</label>
  								<!-- Tab Parameters -->
  								<input type="radio" name="tabset" id="tab-request-params" aria-controls="requestParams" onclick="checkTab(this)">
  								<label id="lbl-request-params" for="tab-request-params">Parameters</label>
  								<!-- Tab Body -->
  								<input type="radio" name="tabset" id="tab-request-body" aria-controls="requestBody" onclick="checkTab(this)">
  								<label id="lbl-request-body" for="tab-request-body">Body</label>
  
  								<div class="tab-panels">
  									<section id="sec-request-header" class="tab-panel">
  									</section>
  									<section id="sec-request-params" class="tab-panel">
  									</section>
  									<section id="sec-request-body" class="tab-panel">
  									</section>
  								</div>
							</div>
						</details>
						<details id="responseDetails">
  							<summary>Response</summary>
  							<div id="response" class="tabset">
  								<!-- Tab Headers -->
  								<input type="radio" name="tabset" id="tab-response-header" aria-controls="responseHeader" disabled>
  								<label id="lbl-response-header" for="tab-response-header">Headers</label>
  								<!-- Tab Body -->
  								<input type="radio" name="tabset" id="tab-response-body" aria-controls="responseBody" disabled>
  								<label id="lbl-response-body" for="tab-response-body">Body</label>
  
  								<div class="tab-panels">
  									<section id="responseHeader" class="tab-panel">
  									</section>
  									<section id="responseBody" class="tab-panel">
  									</section>
  								</div>
							</div>
						</details>
           			</div>
           			</form>
           		</div>
        	</div>
    	</div>

		<script>
			const apiNames = ["Option I", "Option II", "Option III", "Option IV"];
			const baseTabNames = ["header", "params", "body"];
			const current = JSON.parse('{' 
					+ '"envIndex": 0, "funcIndex": 0, '
					+ '"envs": [{' 
						+ '"env": "Local", "route": "localhost:8080"'
					+ '}, {'
						+ '"env": "SIT", "route": "sit.bocaditos.co.uk"'
					+ '}, {'
						+ '"env": "OAT", "route": "oat-bocaditos.co.uk"'
					+ '}, {'
						+ '"env": "PROD", "route": "bocaditos.co.uk"'
					+ '}], '
					+ '"funcs": [{' 
						+ '"func": "customers", "method": "GET", "info": "Gets the customer.", ' 
						+ '"headers": [' 
							+ '{"name": "Content-Type", "type": "text", "value": "application/json", "disabled": true}, ' 
							+ '{"name": "Accept", "type": "text", "value": "application/json", "disabled": true}, ' 
							+ '{"name": "Authorization", "type": "text", "required": true, "info": "Bearer Java Web Token (JWT). It\'s required."}, ' 
							+ '{"name": "User", "type": "text", "pattern": "^[a-zA-Z\d]{1,20}$", "minLength": 1, "maxLength": 20, "error": "Invalid values. Value must be a text between 1 to 20 characters of \'a\' to \'z\' and \'A\' to \'Z\'."}' 
						+ '], ' 
						+ '"body": [' 
								+ '{"name": "Firstname", "type": "text", "required": true, "pattern": "^[a-zA-Z ]{1,20}$", "minLength": 1, "maxLength": 20, "info": "Customer\'s firstname, which it\'s required. Value must be a text between 1 to 20 characters of \'a\' to \'z\' and \'A\' to \'Z\'.", "error": "Invalid values. Value must be a text between 1 to 20 characters of \'a\' to \'z\' and \'A\' to \'Z\'."}, ' 
								+ '{"name": "Surname", "type": "text", "required": true, "pattern": "^[a-zA-Z ]{1,20}$", "minLength": 1, "maxLength": 20, "info": "Customer\'s surname, which it\'s required. Value must be a text between 1 to 20 characters of \'a\' to \'z\' and \'A\' to \'Z\'.", "error": "Invalid values. Value must be a text between 1 to 20 characters of \'a\' to \'z\' and \'A\' to \'Z\'."}, '
								+ '{"name": "Date of birth", "type": "date", "required": true, "info": "Custmer\'s date of birth, which it\'s required.", "error": "Invalid date of birth values."}'
							+ '], ' 
						+ '"params": [' 
							+ ']' 
					+ '}, {'
						+ '"func": "customers", "method": "POST", "info": "Creates the customer.", ' 
							+ '"headers": [], ' 
							+ '"body": [], ' 
							+ '"params": []}, {'
						+ '"func": "customers", "method": "PUT", "info": "Updates the customer.", ' 
							+ '"headers": [], ' 
							+ '"body": [], ' 
							+ '"params": []}, {'
						+ '"func": "customers", "method": "PATCH", "info": "Changes the customer.", ' 
							+ '"headers": [], ' 
							+ '"body": [], ' 
							+ '"params": []}, {'
						+ '"func": "customers", "method": "DELETE", "info": "Deletes the customer.", ' 
							+ '"headers": [], ' 
							+ '"body": [], ' 
							+ '"params": []' 
					+ '}]}');


			function init() {
				var index = 2;
				const menu = document.getElementById("menuItems");

				menu.appendChild(createMenuItem("Home", "home"));
				for (var i = 0; i < apiNames.length; i++) {
					menu.appendChild(createMenuItem(apiNames[i], "option" + (i+1), i == index));
            	}
            	menu.appendChild(document.createElement('hr'));
				menu.appendChild(createMenuItem("Login", "login", i == apiNames.length));
				
				document.title = "Test UI Tool - " + apiNames[index];

				const title = document.getElementById("contentTitle");
				var p = document.createElement('p');	
				
				p.innerHTML = apiNames[index];
				title.appendChild(p);

				const env = document.getElementById("env");

				current.envIndex = -1;
				current.funcIndex = -1;
				while (env.length > 0) {
					env.remove(0);
				}
				for (var i = 0; i < current.envs.length; i++) {
					var opt = document.createElement('option');

				    opt.value = i;
				    opt.innerHTML = current.envs[i].env;
				    env.appendChild(opt);
				}

				const env0 = env.options[0];

				env0.selected = 'selected';
				updateEnv(env0);

				// Handle for details
				const detailsElements = document.querySelectorAll("details");

				detailsElements.forEach(function (item) {
					item.addEventListener("click", handleClickOnDetails);
				});
				openFirstOfDetails();
			}

			function updateEnv(env) {
				const index = env.value;

				if (index != current.envIndex) {
					console.log("Updating environment by event with index " + index + "...");

					current.envIndex = index;
//					env.value = current.envs[index].route;

					const hosts = document.getElementById('host');
					const route = current.envs[current.envIndex].route;

					current.funcIndex = 0;
					while (hosts.firstChild) {
						hosts.removeChild(hosts.lastChild);
					}

					for (var i = 0; i < current.funcs.length; i++) {
						var opt = document.createElement('option');
						var func = current.funcs[i];

					    opt.value = i;
					    opt.title = "Method: " + func.method;
					    opt.innerHTML = route + "/" + func.func;
					    hosts.appendChild(opt);
					}
					hosts.options[0].selected = 'selected';
					current.funcIndex = -1;
					updateHost(host);

					console.log("Environment updated by event with index " + index);
				}
			}

			function updateHost(el) {
				const index = el.value;
	
				console.log("Index: " + index + ", current index: " + current.funcIndex);
				if (index != current.funcIndex) {
					console.log("Updating host by event with index " + index + "...");

					const method = document.getElementById('method');
					let numValidTabs = 0;

					current.funcIndex = index;
					method.value = current.funcs[index].method;
					document.getElementById('info').title=current.funcs[index].info;

					if (buildRequestHeader()) {
						numValidTabs++;
					}
					if (buildRequestParams()) {
						numValidTabs++;
					}
					if (buildRequestBody()) {
						numValidTabs++;
					}
					if (numValidTabs == 3) {
						// Activate sent button
						document.getElementById("send").disabled = false;
					}
					console.log("Host updated by event with index " + index);
				}
			}

			function buildRequestHeader() {
				return buildRequestTab("header", current.funcs[current.funcIndex].headers);
			}

			function buildRequestParams() {
				return buildRequestTab("params", current.funcs[current.funcIndex].params);
			}

			function buildRequestBody() {
				return buildRequestTab("body", current.funcs[current.funcIndex].body);
			}

			function buildRequestTab(name, viewDatas) {
				const secName = "sec-request-" + name;
				const tabName = "tab-request-" + name;
				const section = document.getElementById(secName);

				if (section.firstChild) {
					console.log("Cleaning " + secName + " request tab view...");
					while (section.firstChild) {
						section.removeChild(section.lastChild);
					}
					console.log(secName + " request tab view was cleaned");
				}

				var input = document.getElementById(tabName);

				if (viewDatas.length == 0 && !input.disabled) {

					input.disabled = true;
					console.log(secName + " request tab view is disabled");

					return;
				} else {
					input.disabled = false;
				}

				var table = document.createElement('table');

				console.log("Building " + secName + " request tab view...");
				for (var i = 0; i < viewDatas.length; i++) {
					table.appendChild(buildInput(secName, viewDatas[i], i));
				}
				section.appendChild(table);
				console.log(secName + " request tab view was built");

				return validTab(secName, false);
			}

			function validTab(secName, checkAll) {
				let index = 0;
				let input;

				while (typeof (input = document.getElementById(secName + "-" + index)) != 'undefined' && input != null) {
					if (!input.checkValidity()) {
						// Set header as invalid, i.e. incomplete or error
						let offset = secName.indexOf("-");
						const lblName = "lbl" + secName.substring(offset);
						const lbl = document.getElementById(lblName);

						lbl.className = "invalid";
						document.getElementById("send").disabled = true;

						return false;
					}
					index++;
				}

				let offset = secName.indexOf("-");
				const lblName = "lbl" + secName.substring(offset);
				const lbl = document.getElementById(lblName);

				lbl.className = "";
				if (checkAll) {
					const baseName = secName.substring(0, secName.indexOf("-", "sec-".length) + 1);
					let numValidTabs = 1;

					for (var name of baseTabNames) {
						const nextSecName = baseName + name;

						if (secName != nextSecName && validTab(nextSecName, false)) {
							numValidTabs++;
						}
					}

					if (numValidTabs == baseTabNames.length) {
						// Activate sent button
						document.getElementById("send").disabled = false;
					}
				}

				return true;
			}

			function buildInput(secName, viewData, index) {
				console.log("ViewData: {name: " + viewData.name + ", value: " + viewData.value 
						+ ", placeholder: " + viewData.placeholder 
						+ ", required: " + viewData.required
						+ ", readOnly: " + viewData.readOnly);

				var div = document.createElement('tr');
				var cell = document.createElement('td');
				var label = document.createElement('label');
				var span = document.createElement('span');

				label.innerHTML = viewData.name + ":";
				div.className = "header-field";
				cell.appendChild(label);
				div.appendChild(cell);

				var input = document.createElement('input');

				cell = document.createElement('td');
				input.type = 'text';
				input.id = secName + "-" + index;
				if (typeof viewData.value != 'undefined') {
					input.value = viewData.value;
					console.log("Value was set for " + viewData.name);
				}
				if (typeof viewData.required != 'undefined' && viewData.required) {
					input.required = true;
					console.log("Required was set for " + viewData.name);
				}
				if (typeof viewData.placeholder != 'undefined') {
					input.placeholder = viewData.placeholder;
					console.log("Placeholder was set for " + viewData.name);
				}
				if (typeof viewData.readOnly != 'undefined' && viewData.readOnly) {
					input.readOnly = viewData.readOnly;
					console.log("ReadOnly was set for " + viewData.name);
				}
				if (typeof viewData.disabled != 'undefined' && viewData.disabled) {
					input.disabled = viewData.disabled;
					console.log("Disabled was set for " + viewData.name);
				}
				if (typeof viewData.pattern != 'undefined') {
					input.pattern = viewData.pattern;
				}
				if (typeof viewData.minLength != 'undefined') {
					input.minLength = viewData.minLength;
				}
				if (typeof viewData.maxLength != 'undefined') {
					input.maxLength = viewData.maxLength;
				}
				if (typeof viewData.min != 'undefined') {
					input.min = viewData.min;
				}
				if (typeof viewData.max != 'undefined') {
					input.max = viewData.max;
				}
				if (typeof viewData.error != 'undefined') {
					input.error = viewData.error;
				}
				if (typeof viewData.placeholder != 'undefined') {
					input.placeholder = viewData.placeholder;
				}
				if (typeof viewData.type != 'undefined') {
					input.type = viewData.type;
				}
				if (typeof viewData.disabled == 'undefined' || !viewData.disabled) {
					input.addEventListener('input', setCustomValidity, true);
				}
				cell.appendChild(input);

				let info;

				if (typeof viewData.info != 'undefined') {
					info = viewData.info;
				} else if (viewData.type != 'hidden' 
						&& (typeof viewData.disabled == 'undefined' 
							|| !viewData.disabled)) {
					let length = 0;

					if (typeof viewData.placeholder != 'undefined') {
						info = viewData.placeholder + "\n";
						length = info.length;
					}
					if (typeof viewData.type != 'undefined') {
						info = concatenate(info, length, "Type", viewData.type);
					}
					if (typeof viewData.minLength != 'undefined') {
						info = concatenate(info, length, "Min-Length", viewData.minLength);
					}
					if (typeof viewData.maxLength != 'undefined') {
						info = concatenate(info, length, "Max-Length", viewData.maxLength);
					}
					if (typeof viewData.min != 'undefined') {
						info = concatenate(info, length, "Min", viewData.min);
					}
					if (typeof viewData.max != 'undefined') {
						info = concatenate(info, length, "Max", viewData.max);
					}
					if (typeof viewData.pattern != 'undefined') {
						info = concatenate(info, length, "Pattern", viewData.pattern);
					}
				}
				addInfo(cell, info);
				div.appendChild(cell);

				return div;
			}
			
			function concatenate(txt, length, name, value) {
				if (typeof txt == 'undefined') {
					txt = "";
				} else if (txt.length > length) {
					txt += ", ";
				}
				txt += name + ": '" + value + "'";

				return txt;
			}
			
			function addInfo(div, title) {
				if (typeof title == 'undefined') {
					return;
				}

				var info = document.createElement('button');
				var img = document.createElement('img');

				info.className = "info";
				info.type = "button";

				img.src = "images/info-icon.png";
				img.height = 20;
				img.title = title;
				info.appendChild(img);

				div.appendChild(info);
			}

			function sendForm() {
				// Action from submitting form
console.log("Form: sendForm()");
				fetch(getHost(), {
						method: getMethod(), 
						headers: {}, 
						body: JSON.stringify({})
					})
					.then(response => response.json())
					.then(json => console.log(json))
					.catch(err => console.log(err));
			}
			
			function getMethod() {
				return document.getElementById('method').value;
			}
			
			function getHost() {
				return document.getElementById('host').value;
			}

			function buildUrl() {
				var func = current.funcs[current.funcIndex];
				var params = func.params;
				var url = current.envs[current.envIndex] + "/" + func.func;

				if (params.length > 0) {
					url += "?";
					for (var i = 0; i < params.length; i++) {
						const param = params[i];

						if (i > 0) {
							url += "&";
						}
						
						if (typeof param.value != 'undefined') {
							url += param.name + "=" + param.value;
						} else {
							url += param.name + "=" + getInput("param", i);
						}
					}
				}
				console.log("URL: " + url);

				return url;
			}

			function buildHeaders() {
				const hs = buildObj("header", current.funcs[current.funcIndex].headers);

				console.log("Header: " + hs);

				return hs;
			}

			function buildBody() {
				const body = buildObj("body", current.funcs[current.funcIndex].body);
				
				console.log("Body: " + body);

				return body;
			}

			function buildObj(name, viewdata) {
				if (viewdata.length == 0) {
					return;
				}

				const obj = new Object();

				for (var i = 0; i < viewdata.length; i++) {
					const data = viewdata[i];

					if (typeof data.value != 'undefined') {
						obj[data.name] = header.value;
					} else {
						// Get it from the form
						obj[data.name] = getInput(name, i);
					}
				}

				return obj;
			}

			function getInput(name, index) {
				return document.getElementById(name + index).value;
			}

			function handleClickOnDetails(e) {
//  				console.log("Tab ID: " + e.currentTarget.id);
			}
			
			function openFirstOfDetails() {
  				let details = document.querySelectorAll("details[open]");

				if (details.length > 0) {
					return;
				}

				details = document.querySelectorAll("details");
				if (details.length > 0) {
					details[0].setAttribute("open", true);
				}
			}
			
			function checkTab(item) {
				var parent = item.parentElement;
				var children = parent.childNodes;

  				for (const child of children) {
  					if (child.nodeName == "INPUT") {
						child.checked = child == item;
  					}
  				}
			}

			function setCustomValidity(e) {
				const input = e.currentTarget;
				const offset = input.id.lastIndexOf("-");
				const secName = input.id.substring(0, offset);

				validTab(secName, true);
			}

			function clearForm(e) {
				for (var name of baseTabNames) {
					const secName = "sec-request-" + name;
					let index = 0;

					while (typeof (input = document.getElementById(secName + "-" + index)) != 'undefined' 
							&& input != null) {
						if (!input.disabled) {
							input.value = "";
							if (input.required) {
								document.getElementById("lbl" + secName.substring("sec".length)).className = "invalid";
								document.getElementById("send").disabled = true;
							}
						}
						index++;
					}
				}
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