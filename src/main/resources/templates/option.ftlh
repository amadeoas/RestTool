<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title><${apiName}</title>
		<link rel="shortcut icon" href="#" />
		<link rel="stylesheet" href="main.css">
	</head>
	<body onload="init(${index})">
    	<div id="main">
        	<div id="menu">
        		<div id="menuHead">
        			<div id="title">
	        			<p>${apiName}</p>
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
        			<div id="homeView" hidden>
	        			<div id="contentBHead">
		           			See below a short description and access for each of the APIs supported by this UI.
	        			</div>
	        			<div id="contentCards">
	      		  			<ul id="cards" class="card-container">
	  						</ul>
	        			</div>
        			</div>

					<div id="optionsView" hidden>
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
           			
           			<div id="loginView" hidden>
	           			<p>Right Login</p>
           			</div>
           			
					<div id="errorView" hidden>
					</div>
           		</div>
        	</div>
    	</div>

		<#include "page.ftl" />
	</body>
</html>