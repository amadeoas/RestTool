	class HttpAllSupports {
		constructor(data) {
			this.version = data.version;
			this.name = data.name;
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
	const httpStatus = {"405": ["Method Not Allowed (405)", "The HyperText Transfer Protocol (HTTP) 405 Method Not Allowed response status code indicates that the server knows the request method, but the target resource doesn't support this method."]};
	var apiDetails = new HttpAllSupportsIndex(${data}, ${index});
	var loggedIn = null;


	window.addEventListener("load", function() {
		setTimeout(
			function open() {
				initialiseClose();
			}, 1500 
		)
	});

	function init(index, err) {
		apiDetails.error = err; 

		init(index);
	}

	function init(index) {
		buildMenu(index);

		if (index == -1) {
			// Home page
			const cards = document.getElementById("cards");

			hideIt("env");
			hideIt("optionsView");
			closeLoginPopUp();
			showIt("homeView");
			if (cards.childElementCount == 0) {
				removeAllChildren(cards);
				for (var i = 0; i < apiDetails.supports.length; i++) {
					cards.appendChild(buildCard(i));
				}
			}
		} else if (index == -2) {
			hideIt("homeView");
			hideIt("env");
			hideIt("optionsView");
			closeLoginPopUp();
		} else if (apiDetails.index >= apiDetails.supports.length) {
			// Login page
			hideIt("homeView");
			hideIt("env");
			hideIt("optionsView");
			showLoginPopUp();
		} else {
			// Build one of the options
			document.getElementById("homeView").hidden = true;
			showIt("optionsView"); 

			buildEnvs();
			openFirstOfDetails();
		}
		apiDetails.index = index;

		updateMenuItems();
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

		var item = createMenuItem("Login", "login", apiDetails.supports.length);

		item.onclick = function() {
			showPopupLogin();
		}
		menu.appendChild(item);

		document.title = apiDetails.name;
		if (index >= apiDetails.supports.length) {
			document.title = document.title + " - Login";
		} else if (index >= 0) {
			document.title = document.title + " - " + apiDetails.supports[index].name;
		}

		const title = document.getElementById("contentTitle");
		const p = document.createElement('p');	
		var innerHTML;

		removeAllChildren(title);
		if (index == -1) {
			innerHTML = "Home";
		} else if (index == apiDetails.supports.length) {
			innerHTML = "Login";
		} else if (index == -2) {
			innerHTML = "Error - " + apiDetails.error;
		} else {
			innerHTML = apiDetails.supports[index].name;
		}
		p.innerHTML = innerHTML;
		title.appendChild(p);
	}

	function updateEnv(env) {
		const envIndex = env.value;

		if (envIndex != apiDetails.supports[apiDetails.index].envIndex) {
			console.log("Updating environment by event with index " + envIndex + "...");

			const api = apiDetails.supports[apiDetails.index];

			api.envIndex = envIndex;

			const hosts = document.getElementById('host');
			const route = api.envs[envIndex].route;

			removeAllChildren(hosts);
			for (var i = 0; i < api.funcs.length; i++) {
				var opt = document.createElement('option');
				var func = api.funcs[i];

			    opt.value = i;
			    opt.title = func.method + ((func.info == null) ? "" : ", " + func.info);
			    opt.innerHTML = route + "/" + func.query;
			    hosts.appendChild(opt);
			}
			hosts.options[0].selected = 'selected';
			api.funcIndex = -1;
			updateHost(host);

			console.log("Environment updated by event with index " + envIndex);
		}
	}

	function updateHost(el) {
		const index = el.value;

		console.log("Index: " + index + ", current index: " + apiDetails.supports[apiDetails.index].funcIndex);
		if (index != apiDetails.supports[apiDetails.index].funcIndex) {
			console.log("Updating host by event with index " + index + "...");

			const func = apiDetails.supports[apiDetails.index].funcs[index];
			const method = document.getElementById('method');
			let numValidTabs = 0;
			var info;

			apiDetails.supports[apiDetails.index].funcIndex = index;
			method.value = func.method;
			info = func.info;
			if (typeof info != 'undefined') {
				var title = 'Information on ' + method.value + ': &lt;route&gt;/' + func.query;

				document.getElementById('info').title = info;
				document.getElementById('info').onclick = function() {
					showPopupMsg(title, info);	
				};
			}

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
			table.appendChild(buildInput(secName, viewDatas[i], undefined));
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
	
	function arrayEntry(details, viewData) {
		const numItems = details.childElementCount;

		if (typeof viewData.max != 'undefined' && numItems >= viewData.max) {
			sendError('No more can be added');

			return;
		}

		const items = viewData.items;
		var div = document.createElement('div');
		var table = document.createElement('table');
		var name = viewData.path + '[' + numItems + ']';

		console.log('Building ' + name + ' view...');
		div.id = name;
		if (items.length == 1) {
			table.appendChild(buildInput(name, items[0], 0, (numItems > viewData.min) ? numItems : undefined));
		} else {
			// 
			var fieldset = document.createElement('fieldset');
			var legent = document.createElement('legend');
			var span = document.createElement('span');

			fieldset.appendChild(legent);
			span.innerHTML = viewData.name + ' ' + numItems + ' ';
			legent.appendChild(span);
			if (numItems > viewData.min) {
				// Add remove button, i.e. '-'
				legent.appendChild(buildRemoveBtn(name));
			}

			var table1 = document.createElement('table');

			for (var i = 0; i < items.length; i++) {
				table1.appendChild(buildInput(name, items[i], undefined));
			}
			fieldset.appendChild(appendChild);
			table.appendChild(fieldset);
		}
		div.appendChild(table);
		console.log(name + ' view was built');

		details.appendChild(div);
		if (typeof viewData.max != 'undefined' && (numItems +1) >= viewData.max) {
			// Disable adding new indtances
			var pluss = document.getElementById(details.id + '+');

			pluss.disabled = true;
		}
	}
	
	function buildRemoveBtn(id) {
		var btn = document.createElement('button');

		btn.className = "remove";
		btn.type = "button";
		btn.innerHTML = '-';
		btn.onclick = function() {
			remove(id);	
		};

		return btn;
	}

	function buildInput(secName, viewData, index, indexButton) {
		console.log("ViewData: {name: " + viewData.name + ", value: " + viewData.value 
				+ ", placeholder: " + viewData.placeholder 
				+ ", required: " + viewData.required
				+ ", readOnly: " + viewData.readOnly);

		let info;

		if (viewData.type == "ARRAY") {
			// Create expandable
			var details = document.createElement('details');

			details.id = viewData.path;
			if (typeof viewData.required != 'undefined' && viewData.required) {
				details.setAttribute("open", "");
			}
			
			var summary = document.createElement('summary');
			var field;

			field = document.createElement('span');
			field.innerHTML =  viewData.name + ' (<span class="array-size">from ' + viewData.min + ' to ' + viewData.max + '</span>)';
			summary.appendChild(field);
			field= document.createElement('button');
			field.id = viewData.path + '+';
			field.className = 'add';
			field.innerHTML = '+';
			field.onclick = function() {
				arrayEntry(details, viewData);
			};
			summary.appendChild(field);
			if (typeof viewData.min != 'undefined') {
				info = "Min. length " + viewData.min;
			}
			if (typeof viewData.max != 'undefined') {
				if (typeof viewData.info != 'undefined') {
					info += ', and ';
				}
				info += "Max. length " + viewData.max;
			}
			if (typeof viewData.info != 'undefined') {
				info = viewData.info + '\n\n' + info;
			}
			addInfo(summary, viewData.name, info, indexButton);			
			details.appendChild(summary);

			for (var index = 0; index < viewData.min; index++) {
				arrayEntry(details, viewData);
			}

			return details;
		}

		var div = document.createElement('tr');
		var cell = document.createElement('td');
		var label = document.createElement('label');

		label.innerHTML = viewData.name + (typeof index == 'undefined' ? "" : " " + (index + 1)) + ":";
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
		addInfo(cell, viewData.name, info, indexButton);
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

	function addInfo(div, title, msg, indexButton) {
		if (typeof msg == 'undefined') {
			return;
		}

		var info = document.createElement('button');
		var img = document.createElement('img');

		info.className = "info";
		info.type = "button";

		img.src = "images/info-icon.png";
		img.height = 20;
		img.title = msg;
		info.appendChild(img);

		info.onclick = function() {
			showPopupMsg('Information for field ' + title, msg);	
		};
		div.appendChild(info);

		if (typeof indexButton != 'undefined' && indexButton > 0) {
			div.appendChild(buildRemoveBtn(div.id));
		}
	}

	function remove(id) {
		console.log('Removing ' + id + '...');

		let rmv = document.getElementById(id);

		rmv.parentElemnt.removeChild(rmv);
		if (details.childElementCount < viewData.max) {
			var pluss = document.getElementById(details.id + '+');

			pluss.disabled = false;
		}
		console.log(id + ' was removed');
	}

	function sendForm() {
		// Action from submitting form
		let xhr = new XMLHttpRequest();

		xhr.onload = function() {
			if (xhr.status == 200) {
				responseView(xhr);
			} else {
				responseErrorView(xhr);
			}

			// Open response view
			var element = document.getElementById('responseDetails');

			if (element.hidden) {
				element.hidden = false;
			}
			press(element);
			triggerClick(document.getElementById('tab-response-body'));
		};
		xhr.open('POST', '/execute', true);
		xhr.setRequestHeader('Content-type', 'application/json');
		xhr.setRequestHeader('Accept', 'application/json');
		xhr.send(buildRequest());
	}
	
	function responseView(xhr) {
		console.log("Response: " + JSON.stringify(xhr.response));

		buildResponseBodyView(xhr);
		buildResponseHeadersView(xhr);

		// Open it
		document.getElementById('requestDetails').removeAttribute('open');
		document.getElementById('responseDetails').setAttribute('open', true);
		document.getElementById('sec-response-body').setAttribute('open', true);
	}
	
	function responseErrorView(xhr) {
		console.log('error' + xhr.status + ', "' + xhr.statusText + '"');
		buildResponseHeadersView(xhr);
		buildResponseErrorView(xhr);

		// Open it
		document.getElementById('requestDetails').removeAttribute('open');
		document.getElementById('responseDetails').setAttribute('open', true);
		document.getElementById('sec-response-body').setAttribute('open', true);
	}
	
	function initialiseClose() {
		// Get all the details element
		const details = document.querySelectorAll("details");

		details.forEach((targetDetail) => {
			targetDetail.addEventListener("click", () => {
				// Close all the details that are not targetDetail
				details.forEach((detail) => {
					if (detail !== targetDetail) {
						detail.removeAttribute("open");
					} else if (typeof detail.open != 'undefined') {
						// Make sure one tab is open, if not open Body tab
						var name = targetDetail.id.substring(0, targetDetail.id.length - "Details".length);
						var notFound = true;

						for (var type in baseTabNames) {
							var n = 'tab-' + name + '-' + baseTabNames[type];
							var element = document.getElementById(n);

							if (element != null && typeof element.checked != 'undefined' 
									&& element.checked) {
								notFound = false;

								break;
							}
						}

						if (notFound) {
							// Open Body
							triggerClick(document.getElementById('tab-' + name + '-body'));
						}
					}
				});
		  	});
		});

		document.getElementById('popupLoginClose').addEventListener('click', function() {
			closeLoginPopUp();
			// Keep disabled the options
			loggedIn = null;
			updateMenuItems();
		});
		document.getElementById('popupLoginSubmit').addEventListener('click', function() {
			submitLogin();
		});
		document.getElementById('popupClose').addEventListener('click', function() {
			closeMsgPopUp();
		});
		document.getElementById('popupAClose').addEventListener('click', function() {
			closeMsgPopUp();
		});
	}
	
	function submitLogin() {
		// TODO: disable login, send request, and then update from response
		closeLoginPopUp();
		// Un-disable options
		const time = (1 * 10 * 60 * 1000);

		loggedIn = new Object();
		loggedIn.expires = new Date().getTime() + time;
		loggedIn.timeout = setTimeout(() => {
				console.log("Time out the Logged in!");
				showPopupLogin();
			}, time
		);
		loggedIn.token = '';
		updateMenuItems();
	}
	
	function closeMsgPopUp() {
		document.getElementById('allpopup').style.display = 'none';
	}
	
	function closeLoginPopUp() {
		var allLogin = document.getElementById('allpopupLogin');

		if (allLogin.style.display != 'none') {
			allLogin.style.display = 'none';
		}

		// Disable options
		
	}
	
	function triggerClick(element) {
		var event = new MouseEvent('click', {
			'view': window,
			'bubbles': true,
			'cancelable': true
		});

		element.dispatchEvent(event);
	}
	
	function press(element) {
		dispatchEvent("keypress", element);
	}
	
	function dispatchEvent(action, element) {
		var keyboardEvent = new KeyboardEvent(action, {bubbles: true});

		// You can try charCode or keyCode but they are deprecated
		Object.defineProperty(keyboardEvent, "key", {
  			get() {
    			return "Enter";
  			},
		});
		element.dispatchEvent(keyboardEvent);
	}

	function buildResponseHeadersView(xhr) {
		const headerView = document.getElementById('sec-response-header');
		const headers = xhr.getAllResponseHeaders().trim().split(/[\r\n]+/);

		removeAllChildren(headerView);
		view = document.createElement('table');
		headers.forEach((line) => {
	      	const parts = line.split(': ');
    	  	const header = parts.shift();
      		const value = parts.join(': ');
			var div = document.createElement('tr');
			var cell = document.createElement('td');
			var label = document.createElement('label');
	
			label.innerHTML = header + ":";
			div.className = "header-field";
			cell.appendChild(label);
			div.appendChild(cell);
	
			var input = document.createElement('input');
	
			cell = document.createElement('td');
			input.type = 'text';
			input.value = value;
			input.disabled = true;
			cell.appendChild(input);
			div.appendChild(cell);

			view.appendChild(div);
		});
		headerView.appendChild(view);

		document.getElementById('tab-response-header').disabled = false;
	}

	function buildResponseBodyView(xhr) {
		const bodyView = document.getElementById('sec-response-body');
		let view = document.createElement('textarea');

		removeAllChildren(bodyView);
		view.id = "responseTextarea";
		view.rows = 30;
		view.innerHTML = xhr.response;
		bodyView.appendChild(view);

		document.getElementById('tab-response-body').disabled = (typeof xhr.response == 'undefined');
	}
	
	function buildResponseErrorView(xhr) {
		const bodyView = document.getElementById('sec-response-body');
		var view = document.createElement('p');

		removeAllChildren(bodyView);
		view.id = "errorTitle";
		view.innerHTML = httpStatus[xhr.status][0];
		bodyView.appendChild(view);

		view = document.createElement('p');
		view.id = "errorDescription";
		view.innerHTML = httpStatus[xhr.status][1];
		bodyView.appendChild(view);

		document.getElementById('tab-response-body').disabled = false;
	}

	function buildRequest() {
		const request = new Object();
		const func = apiDetails.supports[apiDetails.index];

		request.apiIndex = apiDetails.index;
		request.envIndex = func.envIndex;
		request.funcIndex = func.funcIndex;
		request.params = buildParams();
		request.headers = buildHeaders();
		request.body = buildBody();

		console.log("Request: ", JSON.stringify(request));

		return JSON.stringify(request);
	}

	function buildParams() {
		return buildObj("param", 
				apiDetails.supports[apiDetails.index].funcs[apiDetails.supports[apiDetails.index].funcIndex].params);
	}

	function buildHeaders() {
		return buildObj("header", 
				apiDetails.supports[apiDetails.index].funcs[apiDetails.supports[apiDetails.index].funcIndex].headers);
	}

	function buildBody() {
		return buildObj("body", 
				apiDetails.supports[apiDetails.index].funcs[apiDetails.supports[apiDetails.index].funcIndex].body);
	}

	function buildObj(name, viewdata) {
		if (viewdata == null || viewdata.length == 0) {
			return;
		}

		const obj = new Object();

		for (var i = 0; i < viewdata.length; i++) {
			const data = viewdata[i];

			if (typeof data.value != 'undefined') {
				obj[data.name] = data.value;
			} else {
				// Get it from the form
				const value = getInput(name, i);

				if (value != 'undefined' & value.length > 0) {
					obj[data.name] = getInput(name, i);
				}
			}
		}

		return obj;
	}

	function getInput(name, index) {
		return document.getElementById('sec-request-' + name + '-' + index).value;
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
		document.getElementById('responseDetails').hidden = true;
		document.getElementById('requestDetails').open = true;
		triggerClick(document.getElementById('tab-request-body'));
	}

	function createMenuItem(name, pageHref, index) {
		// <p><a href="home.html">Home</a></p>
		var p = document.createElement('p');
		var a = document.createElement('a');

		a.id = 'menu-item-' + index;
		if (index == apiDetails.supports.length) {
			a.href = "#";
			a.onclick = function() {
				showPopupLogin();	
			};
		} else {
			a.href = "javascript:init(" + index + ")";
		}
		a.innerHTML = name;
		p.appendChild(a);

		return p;
	}

	function updateMenuItems() {
		var a;

		// Home
		a = document.getElementById('menu-item--1');
		if (apiDetails.index == -1) {
			a.className = 'disable-link';
		} else {
			a.className = '';
		}

		// Options
		var i = 0;
		var btn;

		for (; i < apiDetails.supports.length; ++i) {
			a = document.getElementById('menu-item-' + i);
			if (loggedIn == null || i == apiDetails.index) {
				a.className = 'disable-link';
			} else {
				a.className = '';
			}
			btn = document.getElementById('card-btn-' + i);
			if (btn != null) {
				btn.className = a.className + '-button';
			}
		}
		
		// Login
		a = document.getElementById('menu-item-' + i);
		if (loggedIn == null) {
			a.innerHTML = 'Login';
		} else {
			a.innerHTML = 'Logout';
		}
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
		button.id = 'card-btn-' + i;
		button.type = "button";
		button.innerHTML = apiDetails.supports[i].name;
		button.onclick = function() {init(i);};
		div.appendChild(button);
		card.appendChild(div);

		li.appendChild(card);

		return li;
	}

	function showPopupMsg(title, msg) {
		document.getElementById('popupTitle').innerHTML = title;
		document.getElementById('popupMsg').innerHTML = msg;
		document.getElementById('allpopup').style.display = 'block';
	}

	var loginIndex = 0;

	function showPopupLogin() {
		const a = document.getElementById('menu-item-' + apiDetails.supports.length);

		if (a.innerHTML == 'Logout') {
			if (loggedIn != null) {
				clearTimeout(loggedIn.timeout);
			}
			loginIndex = 1;
			loggedIn = null;
			if (apiDetails.index != -1) {
				// Move to home
				init(-1);
			} else {
				updateMenuItems();
			}
		} else if (loginIndex == 1) {
			loginIndex = 0;
		} else {
			document.getElementById('allpopupLogin').style.display = 'block';
		}
	}
	
	function sendError(msg) {
		console.log('Error: ' + msg);
	}
