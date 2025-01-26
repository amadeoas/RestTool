var loggedIn = null;
var data = null;
var filesData = null;
var optionsView;
var homeView;
var showView;
var btnFiles;
var footer;
var errorView;

document.getElementById("filesForm").addEventListener("submit", readFiles);


function init_() {
	optionsView = document.getElementById("optionsView");
	showView = document.getElementById("showView");
	btnFiles = document.getElementById("btnFiles");
	footer = document.getElementById("footer");

	errorView = document.getElementById("errorView");
}

function changeInput() {
	btnFiles.disabled = false;
}

function error(msg) {
	message("ERROR", msg);
}

function warn(msg) {
	message("WARN", msg);
}

function info(msg) {
	message("INFO", msg);
}

function debug(msg) {
	message("DEBUG", msg);
}

function trace(msg) {
	message("TRACE", msg);
}

function message(type, msg) {
	var elm;

	elm = document.createElement("p");
	if (type != null) {
		if (type == "ERROR") {
			elm.class = "error";
		} else if (type == "WARN") {
			elm.class = "warning";
		} else if (type == "INFO") {
			elm.class = "info";
		} else if (type == "DEBUG") {
			elm.class = "debug";
		} else if (type == "TRACE") {
			elm.class = "trace";
		type = "TRACE";
		}

		elm.innerHTML = type + ": " + msg;
		footer.prepend(elm);
	}
	console.log(msg);
}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function updateFiles() {
	var numSleeps = 20;

	while (filesData.numLoadedFiles < filesData.numValidFiles && filesData.noError
			&& numSleeps > 0) {
		await sleep(200);
		--numSleeps;
	}

	if (filesData.numLoadedFiles < filesData.numValidFiles) {
		warn("Not all files were loaded so content wasn't sent to server");
	} else if (filesData.noError) {
		if (filesData.length == 0) {
			warn("Noting to show!");

			return;
		}
		info("All files were loaded");

		info("Processing loaded files...");
		filesData.noError = undefined;
		filesData.length = undefined;
		filesData.numLoadedFiles = undefined;
		filesData.numValidFiles = undefined;
		filesData.files = undefined;

		// Send logs to server to process
		var xmlhttp = new XMLHttpRequest();

		xmlhttp.open("POST", "/readFiles", true);
   		xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
   		xmlhttp.setRequestHeader("Accept", "application/json");
   		xmlhttp.onreadystatechange = () => {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == 200) {
					data = xmlhttp.responseText;
				} else {
					error("[" + xmlhttp.status + "] Failed to process files!");
				}
			}
		};
   		xmlhttp.send(JSON.stringify(filesData));
   		filesData = undefined;

		// Present logs
		info("Loaded files were processed");
	}

	filesData = null;
	enableForm(true);
}

function processFiles() {
	info("Starting the load of files...");

	filesData.noError = true;
	filesData.contents = [];
	for (var i = 0 ; i < filesData.files.length; ++i) {
		var file = filesData.files[i];

		if (file.name.endsWith(".log")) {
			var reader = new FileReader();

			reader.index = i;
			reader.addEventListener("error", () => {
				error(error.message); 
				filesData.noError = false;
			});
			reader.onload = function() {
    			// By lines
    			var from = new Date(filesData.from);
    			var to = new Date(filesData.to);
    			const text = this.result;
				var lines = text.split('\n');
				var content = "";

				for (var index = 0; index < lines.length; ++index) {
					const line = lines[index];
					var d = line.substring(1, 5) + "-" + line.substring(5, 7) + "-" 
							+ line.substring(7, 9) + "T" + line.substring(10, 22) + "Z";
					var date = new Date(d);

					if (date < from) {
						continue;
					}
					if (date > to) {
						break;
					}
					if (content.length > 0) {
						content += "\n";
					}
					content += line;
				}

				var obj = new Object();

				obj.content = content;
				obj.file = filesData.files[this.index].name;
				filesData.contents[this.index] = obj;
				++filesData.numLoadedFiles;
				if (obj.content.length == 0) {
					debug("Nothing to show for file " + obj.file);

					return;
				}
				filesData.length += obj.content.length;
				info("Loaded file \"" + filesData.files[this.index].name + "\"");
			};
			reader.readAsText(file);
			filesData.contents[reader.index] = reader.result;
			++filesData.numValidFiles;
		}
	}

	if (filesData.numValidFiles == 0) {
		// Not all log files were load
		info("Nothing to load");
		showLogs(false);
	} else {
		info("Load of files was started");
		showLogs(true);

		info("Waiting to load all files...");
		setTimeout(updateFiles, 100)
	}
}

async function readFiles(event) {
	if (typeof event !== 'undefined') {
		event.preventDefault(); // stop form from submitting

		return;
	}

	info("Preparing reading of files...");
	enableForm(false);

	var fileList = document.getElementById("fileInput");
	var element;

	data = null;
	filesData = new Object();
	filesData.numValidFiles = 0;
	filesData.numLoadedFiles = 0;
	filesData.length = 0;
	filesData.files = fileList.files;
	element = document.getElementById("fromDate");
	filesData.from = element.value + ".000Z"; // yyyy-MM-ddTHH:mm:ss
	element = document.getElementById("toDate");
	filesData.to = element.value + ".000Z"; // yyyyMMdd HH:mm:ss
	fileList.innerHTML = ""; // clear previous file list

	info("Reading of files was prepared");
	processFiles();
}

function showLogs(enabled) {
	enableForm(!enabled);
	homeView.hidden = enabled;
	optionsView.hidden = !enabled;
}

function enableForm(enabled) {
	var form = document.getElementById("filesForm");

	form.disabled = !enabled;
}

//-----------------------------------
var W;
var H;
var w0 = ${w_0};
var wmax = ${w_max};
var wj;
const RECT_WIDTH = ${w_rect};
const h = ${h};
const minHeight = ${minHeight};
const REQUEST_RECEIVED = '${requestReceived}';
const REQUEST_RESPONSE = '${requestResponse}';
const SEND_REQUEST  = '${sendReques}';
const SEND_RESPONSE = '${sendResponse}';
var logs = ${logs};
var heightSize;


function init() {
	init_();

	var container = document.getElementById('contentBody');
	var view = document.getElementById('flowView');
	var bound = container.getBoundingClientRect();

	W = bound.width;
	wj = (W - (2 * w0)) / (logs.apps.length - 1);
	if (wj > wmax) {
		W = (2 * w0) + (wmax * (logs.apps.length - 1));
		wj = wmax;
	}
	H = bound.height;
	view.style.width = W + 'px';
	view.style.height = H + 'px';
	view.style.top = '${flowView_top}';
	view.innerHTML = '';
	heightSize = buildDate(logs.to) - buildDate(logs.from);
	for (var appIndex = 0; appIndex < logs.apps.length; ++appIndex) {
		var appView = buildAppView(appIndex);

		view.appendChild(appView);
	}
}

function buildAppView(appIndex) {
	const app = logs.apps[appIndex];
	var appView = document.createElement('div');
	var hr;

	appView.name = appView.id = app.name;
	appView.appIndex = appIndex;
	appView.classList.add('app');
	appView.style.left = getLeft(app) + 'px';
	appView.style.width = RECT_WIDTH + 'px';
	appView.style.height = H + 'px';

	hr = document.createElement('hr');
	hr.width = '1';
	hr.size = H;
	hr.classList.add('hrLine');
	appView.appendChild(hr);

	for (var index = 0; index < app.logs.length; ++index) {
		buildLogsMsgView(appView, app, index);
	}

	return appView;
}

function buildLogsMsgView(appView, app, logsMsgIndex) {
	const logsMsg = app.logs[logsMsgIndex];
	var logsMsgView = document.createElement('div');

	logsMsgView.name = logsMsgView.id = app.name + ':' + logsMsgIndex;
	logsMsgView.appIndex = app.index;
	logsMsgView.index = logsMsgIndex;
	logsMsgView.classList.add('logsMsg');
	logsMsgView.style.top = getTop(logsMsg) + 'px';
	logsMsgView.style.width = RECT_WIDTH + 'px';
	logsMsgView.style.height = getHeigh(logsMsg) + 'px';
	logsMsgView.onclick = showShowView;
	logsMsgView.onmouseover = showTooltip;
	logsMsgView.onmouseout = hideTooltip;
	appView.appendChild(logsMsgView);

	if (logsMsg.type == REQUEST_RECEIVED || logsMsg.type == REQUEST_RESPONSE) {
		// Horizontal line before with 1/2 separation between apps view
		var hr = document.createElement('span');
		var w = (app.index == 0) ? w0 : (wj / 2);

		hr.id = 'arrw-' + logsMsgView.id;
		hr.style.top = (parseInt(logsMsgView.style.top) + 2) + 'px';
		hr.style.left = (parseInt(appView.style.left) - w + 2) + 'px';
		hr.style.width = w + 'px';
		hr.classList.add('hrArrow');
		hr.classList.add('line');
		if (logsMsg.type == REQUEST_RECEIVED) {
			hr.classList.add('arrow-right');
		} else {
			hr.classList.add('arrow-left');
		}
		document.getElementById('flowView').appendChild(hr);
	} else if (logsMsg.type == SEND_REQUEST || logsMsg.type == SEND_RESPONSE) {
		// Horizontal line after with 1/2 separation between apps view
		var hr = document.createElement('span');
		var w = wj / 2;

		hr.id = 'arrw-' + logsMsgView.id;
		hr.style.top = (parseInt(logsMsgView.style.top) + 2) + 'px';
		hr.style.left = (parseInt(appView.style.left) + RECT_WIDTH + 2) + 'px';
		hr.style.width = (w - RECT_WIDTH) + 'px';
		hr.classList.add('hrArrow');
		hr.classList.add('line');
		if (logsMsg.type == SEND_REQUEST) {
			hr.classList.add('arrow-right');
		} else {
			hr.classList.add('arrow-left');
		}
		document.getElementById('flowView').appendChild(hr);
	}

	return logsMsgView;
}

function getTop(logsMsg) {
	var top = parseInt(h + (((H - (2 * h)) * (buildDate(logsMsg.from) - buildDate(logs.from))) / heightSize), 10);

	return top;
}

function getLeft(app) {
	return getLeft_(app.index);
}

function getLeft_(index) {
	if (index == 0) {
		return w0;
	}

	var left = parseInt(w0 + (((W - (2 * w0)) * index) / (logs.apps.length - 1)), 10);

	return left;
}

function  getHeigh(logsMsg) {
	var height = parseInt(((H - (2 * h)) * (buildDate(logsMsg.to) - buildDate(logsMsg.from))) / heightSize, 10);

	if (height < minHeight) {
		height = minHeight;
	}

	return height;
}

function buildDate(datetime) {
	datetime = datetime.replace(' ', 'T');

	return Date.parse(datetime);
}

function getTooltip() {
	var tooltip = document.getElementById('tooltip');

	if (tooltip == null) {
		tooltip = document.createElement('div');
		tooltip.id = 'tooltip';
		tooltip.hidden = true;
		document.getElementById('flowView').appendChild(tooltip);
	}

	return tooltip;
}

function getShowView() {
	var showView = document.getElementById('showView');

	if (showView == null) {
		var opac = document.createElement('div');

		opac.id = 'opac';
		document.body.appendChild(opac);

		showView = document.createElement('div');
		showView.id = 'showView';
		showView.hidden = true;

		showView.setAttribute("class", "callout");
		showView.dataClosable = true;

		// Top
		div = document.createElement('div');
		div.style.width = '100%';
		div.style.height = '32px';
		div.id = 'topShowView';
		elem = document.createElement("span");
		elem.id = 'showViewTitle';
		elem.style.fontSize = '14px';
		elem.innerHTML = 'Logged Messages';
		div.appendChild(elem);
		elem = document.createElement("button");
		elem.id = 'showViewBtnCloseTop';
		elem.setAttribute("class", "close");
		elem.type = "button";
		elem.innerHTML = 'X';
//		elem.style.float= 'right';
		elem.classList.add('favorite');
		elem.classList.add('xstyled');
		elem.onclick = hideShowView
		div.appendChild(elem);
		showView.appendChild(div);

		elem = document.createElement('hr');
		showView.appendChild(elem);

		div = document.createElement('div');
		div.id = 'headerView';
		showView.appendChild(div);

		// Middle
		div = document.createElement('div');
		div.id = 'msgsView';
		showView.appendChild(div);

		// Bottom
		div = document.createElement('hr');
		showView.appendChild(div);

		div = document.createElement('div');
		div.id = 'bottomShowView';
		elem = document.createElement("button");
		elem.setAttribute("class", "close");
		elem.type = "button";
		elem.innerHTML = 'Close';
		elem.style.float= 'right';
		elem.classList.add('favorite');
		elem.classList.add('styled');
		elem.onclick = hideShowView
		elem.id = 'showViewBtnClose';
		div.appendChild(elem);
		showView.appendChild(div);
		opac.appendChild(showView);
	}

	return showView;
}

function showOpac() {
	var opac = document.getElementById('opac');

	opac.style.display = '';

	return false;
}

function hideOpac() {
	var opac = document.getElementById('opac');

	opac.style.display = 'none';

	return false;
}

function showShowView(event) {
	hideShowView();
	showOpac();

	var divId = event.currentTarget.id;
	var app = getApp(divId);
	var logsMsg = getLogsMsg(divId);
	var showView = getShowView();
	var header = document.getElementById('headerView');
	var msgView;

	header.innerHTML = '<span>App Name: </span><span id="svApiName">' + app.name + ', </span>'
		+ '<span class="headerTxt">Type: </span><span id="svLogType">' + logsMsg.type + ', </span>'
		+ '<span class="">From: </span><span id="svLogFrom">' + logsMsg.from + ', </span>'
		+ '<span class="">To: </span><span id="svLogTo">' + logsMsg.to + '</span>\n'
		+ '<hr />';

	msgView = document.getElementById('msgsView');
	for (index = 0; index < logsMsg.logs.length; ++index) {
		msgView.innerHTML += '<p>' + logsMsg.logs[index].msg + '</p>\n';
	}
	hideTooltip();
	showView.style.display = '';

	return false;
}

function showTooltip(event) {
	hideTooltip();

//	var div = document.getElementById('flowView');
	var top  = event.clientY  + 'px';
	var divId = event.currentTarget.id;
	var app = getApp(divId);
	var logsMsg = getLogsMsg(divId);
	var tooltip = getTooltip();
	var arrow = document.getElementById('arrw-' + divId);

	tooltip.style.left = appRight(divId);
	tooltip.style.top = top;
	tooltip.innerHTML = '<p>App Name: ' + app.name + '</p>'
		+ '<p>Type: ' + logsMsg.type + '</p>'
		+ '<p>From: ' + logsMsg.from + '</p>'
		+ '<p>To: ' + logsMsg.to + '</p>';
	var bound = tooltip.getBoundingClientRect();

	if (bound.width + parseInt(tooltip.style.left) > W) {
		tooltip.style.left = (parseInt(tooltip.style.left) - (bound.width + parseInt(event.currentTarget.style.width))) + 'px';
	}
	if (bound.height + parseInt(tooltip.style.top) > H) {
		tooltip.style.top = (parseInt(tooltip.style.top) - bound.height) + 'px';
	}
	tooltip.style.display = '';
	if (arrow != undefined) {
		arrow.style['border-bottom-width'] = '2px';
	}

	return false;
}

function appRight(logsMsgId) {
	var view = document.getElementById('flowView');
	var appId = getAppId(logsMsgId);
	var app = document.getElementById(appId);
	var i = parseInt(view.style.left) + parseInt(app.style.left) 
		+ parseInt(app.style.width);

	return i + "px";
}

function hideTooltip(event) {
	var tooltip = getTooltip();

	if (event != undefined) {
		var divId = event.currentTarget.id;
		var arrow = document.getElementById('arrw-' + divId);

		if (arrow != undefined) {
			arrow.style['border-bottom-width'] = '1px';
		}
	}

	tooltip.innerHTML = '';
	tooltip.style.display = 'none';

	return false;
}

function hideShowView() {
	var view = getShowView();

	hideOpac();
	view.style.display = 'none';
	view = document.getElementById('msgsView');
	view.innerHTML = '';

	return false;
}

function getApp(logsMsgId) {
	var appName = getAppId(logsMsgId);

	for (var appIndex = 0; appIndex < logs.apps.length; ++appIndex) {
		var app = logs.apps[appIndex];

		if (app.name == appName) {
			return app;
		}
	}

	return null;
}

function getAppId(lodsMsgId) {
	var offset = lodsMsgId.indexOf(':');

	return (offset == -1) ? logsMsgId : lodsMsgId.substring(0, offset);	
}

function getLogsMsg(logsMsgId) {
	var app = getApp(logsMsgId);

	if (app == null) {
		return null;
	}

	var offset = logsMsgId.indexOf(':');
	var logsMsgIndex = logsMsgId.substring(++offset);

	if (logsMsgIndex < 0 || logsMsgIndex >= app.logs.length) {
		return null;
	}

	return app.logs[logsMsgIndex];
}