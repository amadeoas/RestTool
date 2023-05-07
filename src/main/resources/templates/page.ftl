<script type="text/javascript">
	class HttpAllSupports {
		constructor(data) {
			this.version = data.version;
			this.supports = data.supports;
		}
	};
	
	class HttpAllSupportsIndex extends HttpAllSupports {
		constructor(data, index) {
			super(data);
			this.index = index;
		}
	};
	
	const baseTabNames = ["header", "params", "body"];
	var apiDetails = new HttpAllSupportsIndex(${data}, ${index});
	
	
	function init(index) {
		buildMenu(index);

		if (index < 0) {
			// Home page
			const cards = document.getElementById("cards");

			hideIt("env");
			hideIt("optionsView");
			hideIt("loginView");
			showIt("homeView");
			removeAllChildren(cards);
			for (var i = 0; i < apiDetails.supports.length; i++) {
				cards.appendChild(buildCard(i));
			}
	
			return;
		} else if (apiDetails.index >= apiDetails.supports.length) {
			// Login page
			hideIt("homeView");
			hideIt("env");
			hideIt("optionsView");
			showIt("loginView");

			return;
		}

		// Build one of the options
		document.getElementById("homeView").hidden = true;
		document.getElementById("loginView").hidden = true;
		showIt("optionsView"); 

		buildEnvs();
	
		// Handle for details
		const detailsElements = document.querySelectorAll("details");
	
		detailsElements.forEach(function (item) {
			item.addEventListener("click", handleClickOnDetails);
		});
		openFirstOfDetails();
	}
	
	function buildEnvs() {
		const env = document.getElementById("env");
		const details = apiDetails.supports[apiDetails.index];
	
		removeAllChildren(env);
		showIt("env");
		for (var i = 0; i < details.envs.length; i++) {
			var opt = document.createElement('option');
	
			opt.value = i;
			opt.innerHTML = details.envs[i].name;
			env.appendChild(opt);
		}
	
		const envX = env.options[details.envIndex];
	
		envX.selected = 'selected';
		details.envIndex = -1; // to make sure to update view as this is first time
		updateEnv(envX);
	}
	
	function hideIt(name) {
		document.getElementById(name).setAttribute("hidden", "");
	}
	
	function showIt(name) {
		document.getElementById(name).removeAttribute("hidden");
	}
	
	function removeAllChildren(node) {
		if (node == null) {
			return;
		}
		node.textContent = '';
	}
	
	function buildMenu(index) {
		const menu = document.getElementById("menuItems");
	
		apiDetails.index = index;
		removeAllChildren(menu);
		menu.appendChild(createMenuItem("Home", "home", -1));
		for (var i = 0; i < apiDetails.supports.length; i++) {
			menu.appendChild(createMenuItem(apiDetails.supports[i].name, "option" + (i+1), i));
		}
		menu.appendChild(document.createElement('hr'));
		menu.appendChild(createMenuItem("Login", "login", apiDetails.supports.length));
	
		document.title = "REST API UI Tool";
		if (index >= apiDetails.supports.length) {
			document.title = document.title + " - Login";
		} else if (index >= 0) {
			document.title = document.title + " - " + apiDetails.supports[index].name;
		}
	
		const title = document.getElementById("contentTitle");
		const p = document.createElement('p');	
		
		removeAllChildren(title);
		p.innerHTML = index < 0 ? "Home" : ((index >= apiDetails.supports.length) ? "Login" : apiDetails.supports[index].name);
		title.appendChild(p);
	}
	
	function updateEnv(env) {
		const index = env.value;
	
		if (index != apiDetails.supports[apiDetails.index].envIndex) {
			console.log("Updating environment by event with index " + index + "...");
	
			apiDetails.supports[apiDetails.index].envIndex = index;
	
			const hosts = document.getElementById('host');
			const route = apiDetails.supports[apiDetails.index].envs[apiDetails.supports[apiDetails.index].envIndex].route;
	
	//		apiDetails.supports[apiDetails.index].funcIndex = 0;
			removeAllChildren(hosts);
			for (var i = 0; i < apiDetails.supports[apiDetails.index].funcs.length; i++) {
				var opt = document.createElement('option');
				var func = apiDetails.supports[apiDetails.index].funcs[i];
	
			    opt.value = i;
			    opt.title = "Method: " + func.method;
			    opt.innerHTML = route + "/" + func.func;
			    hosts.appendChild(opt);
			}
			hosts.options[0].selected = 'selected';
			apiDetails.supports[apiDetails.index].funcIndex = -1;
			updateHost(host);
	
			console.log("Environment updated by event with index " + index);
		}
	}
	
	function updateHost(el) {
		const index = el.value;
		
		console.log("Index: " + index + ", current index: " + apiDetails.supports[apiDetails.index].funcIndex);
		if (index != apiDetails.supports[apiDetails.index].funcIndex) {
			console.log("Updating host by event with index " + index + "...");
	
			const method = document.getElementById('method');
			let numValidTabs = 0;
	
			apiDetails.supports[apiDetails.index].funcIndex = index;
			method.value = apiDetails.supports[apiDetails.index].funcs[index].method;
			document.getElementById('info').title = apiDetails.supports[apiDetails.index].funcs[index].info;
	
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
		return buildRequestTab("header", apiDetails.supports[apiDetails.index].funcs[apiDetails.supports[apiDetails.index].funcIndex].headers);
	}
	
	function buildRequestParams() {
		return buildRequestTab("params", apiDetails.supports[apiDetails.index].funcs[apiDetails.supports[apiDetails.index].funcIndex].params);
	}
	
	function buildRequestBody() {
		return buildRequestTab("body", apiDetails.supports[apiDetails.index].funcs[apiDetails.supports[apiDetails.index].funcIndex].body);
	}
	
	function buildRequestTab(name, viewDatas) {
		const secName = "sec-request-" + name;
		const tabName = "tab-request-" + name;
		const section = document.getElementById(secName);
		const input = document.getElementById(tabName);
	
		removeAllChildren(section);
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
		} else if (viewData.type != 'hidden' && (typeof viewData.disabled == 'undefined' 
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
		var func = apiDetails.supports[apiDetails.index].funcs[apiDetails.supports[apiDetails.index].funcIndex];
		var params = func.params;
		var url = apiDetails.supports[apiDetails.index].envs[apiDetails.supports[apiDetails.index].envIndex] + "/" + func.func;
	
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
		const hs = buildObj("header", 
				apiDetails.supports[apiDetails.index].funcs[apiDetails.supports[apiDetails.index].funcIndex].headers);
	
		console.log("Header: " + hs);
	
		return hs;
	}
	
	function buildBody() {
		const body = buildObj("body", 
				apiDetails.supports[apiDetails.index].funcs[apiDetails.supports[apiDetails.index].funcIndex].body);
		
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
	//  	console.log("Tab ID: " + e.currentTarget.id);
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
	
	function createMenuItem(name, pageHref, index) {
		var p = document.createElement('p');
	
		if (index == apiDetails.index) {
			// <p class="disabled">Option 1</p>
			p.innerHTML = name;
			p.className = "disabled";
		} else {
			// <p><a href="home.html">Home</a></p>
			var a = document.createElement('a');
	
			if (index < 0 || index >= apiDetails.supports.length) {
				a.href = pageHref;
			} else if (apiDetails.index == -1) {
				a.href = "option" + (index + 1);
			} else {
				a.href = "javascript:init(" + index + ")";
			}
			a.innerHTML = name;
			p.appendChild(a);
		}
	
		return p;
	}
	
	function buildCard(i) {
		const li = document.createElement('li');
		const card = document.createElement('div');
		var div;
		var button;
	
		card.className = "card";
	
		div = document.createElement('div');
		div.innerHTML = apiDetails.supports[i].name;
		div.className = "cardHeader";
		card.appendChild(div);
	
		div = document.createElement('div');
		div.innerHTML = apiDetails.supports[i].info;
		div.className = "container";
		card.appendChild(div);
	
		div = document.createElement('div');
		div.className = "cardFooter";
		button = document.createElement('button');
		button.type = "button";
		button.innerHTML = apiDetails.supports[i].name;
		button.onclick = function() {init(i);};
//		button.addEventListener('click', function() {
//				init(i);
//			}, false);
		div.appendChild(button);
		card.appendChild(div);
	
		li.appendChild(card);
	
		return li;
	}
</script>
