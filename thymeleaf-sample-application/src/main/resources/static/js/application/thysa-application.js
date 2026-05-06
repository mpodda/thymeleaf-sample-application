/*
----------------
-- AJAX Calls --
----------------
 */

export function getSync(uri) {
	const httpRequest = new XMLHttpRequest();
	httpRequest.open("GET", uri, false);
	httpRequest.send();
	
	return httpRequest;
}


export async function postFormSync(form, uri) {
	//const form = document.querySelector(`#${formElementId}`);
	
	const params = new FormData(form);
	let httpRequest = new XMLHttpRequest();
	httpRequest.open("POST", uri, false);
	httpRequest.send(params);
	
	return httpRequest;
}


/*
export async function setContent(htmlText, contentPlaceholderId) {
	let contentText = createNodeFromText(htmlText);
	
//	console.info("contentText=", contentText);
	
	try {
		
		console.info(document.querySelector(`#${contentPlaceholderId}`).childNodes, "childNodes");
		
		if (document.querySelector(`#${contentPlaceholderId}`).hasChildNodes()) {
			for (const childNode of document.querySelector(`#${contentPlaceholderId}`).childNodes) {
				console.info("childNode:", childNode.tagName);
				
				if (childNode.nodeType === Node.ELEMENT_NODE || childNode.nodeType === Node.TEXT_NODE) {
					if (childNode.tagName == "SECTION") {
						document.querySelector(`#${contentPlaceholderId}`).replaceChild(contentText, childNode);
						break;
					}
				}
			}
		} else {
			document.querySelector(`#${contentPlaceholderId}`).appendChild(contentText);
		}
	} catch (e) {
		document.querySelector(`#${contentPlaceholderId}`).appendChild(contentText);
	}
	
	return contentText;
}
*/

export async function setContent(htmlText, contentPlaceholder) {
	let contentText = createNodeFromText(htmlText);
	
	console.info("contentText=", contentText);
	let replaced = false;
	
	try {
		if (contentPlaceholder.hasChildNodes()) {
			for (const childNode of contentPlaceholder.childNodes) {
				console.info("childNode:", childNode.tagName);
				
				if (childNode.nodeType === Node.ELEMENT_NODE || childNode.nodeType === Node.TEXT_NODE) {
					if (childNode.tagName == "SECTION") {
						contentPlaceholder.replaceChild(contentText, childNode);
						replaced = true;
						break;
					}
				}
			}
		} else {
			contentPlaceholder.appendChild(contentText);
		}
	} catch (e) {
		contentPlaceholder.appendChild(contentText);
	}
	
	console.info("replaced: ", replaced);
	
	return contentText;
}

export function createHTMLFragment(htmlText, fragmentId) {
	let fragmentText = document.createElement("body");
	fragmentText.innerHTML = htmlText;
	
	let fragment = fragmentText.querySelector(`#${fragmentId}`);
	
	return document.importNode(fragment, true);
}

export function createHTMLFragmentFromTag(htmlText, tagName) {
	let fragmentFromHtmlText = document.createElement("section");
	fragmentFromHtmlText.innerHTML = htmlText;
	let htmlFragment = fragmentFromHtmlText.getElementsByTagName(tagName)[0];
	
	return document.importNode(htmlFragment, true);
}


export function createNodeFromText(text) {
	let fragmentText = document.createElement("section");
	fragmentText.innerHTML = text;
	
	return document.importNode(fragmentText, true);
}


/*
  -----------------------
  -- Intecommunication -- 
  -----------------------
*/

export class Intecommunication {
	constructor() {
		this.onDataChange = async () => {};
		
		this.onDataChange = async (data) => {};
	}
}


export let intecommunication = new Intecommunication();

