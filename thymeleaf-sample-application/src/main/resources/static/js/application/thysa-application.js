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
	const params = new FormData(form);
	let httpRequest = new XMLHttpRequest();
	httpRequest.open("POST", uri, false);
	httpRequest.send(params);
	
	return httpRequest;
}


/*
  -------------------------
  -- DHTML Functionality --
  -------------------------
*/


const ValidChildNodePlaceholderTags = Object.freeze({
	SECTION : Symbol("SECTION"),
	    DIV : Symbol("DIV"),
	   SPAN : Symbol("SPAN"),
	      P : Symbol("P"),
	    NAV : Symbol("NAV"),
	  TABLE : Symbol("TABLE")
});

function getValidChildNode(parentNode) {
	if (parentNode.hasChildNodes()) {
		for (const childNode of parentNode.childNodes) {
			if (childNode.nodeType === Node.ELEMENT_NODE || childNode.nodeType === Node.TEXT_NODE) {
				if (Object.keys(ValidChildNodePlaceholderTags).includes(childNode.tagName)) {
					return childNode;
				}
			}
		}
	}
	
	return null;
}

export async function setContentFromText(htmlText, contentPlaceholder) {
	let contentNode = createNodeFromText(htmlText);

	return await setContentFromFragment(contentNode, contentPlaceholder);	
}

export async function setContentFromFragment(contentNode, contentPlaceholder) {
	let replaced = false;

	try {
		/*
		if (contentPlaceholder.hasChildNodes()) {
			for (const childNode of contentPlaceholder.childNodes) {
				console.info("childNode:", childNode.tagName);
				
				if (childNode.nodeType === Node.ELEMENT_NODE || childNode.nodeType === Node.TEXT_NODE) {
					if (Object.keys(ValidChildNodePlaceholderTags).includes(childNode.tagName)) {
						console.info("contentNode=", contentNode);
						console.info("childNode=", childNode);
						
						//contentPlaceholder.replaceChild(contentNode, childNode);
						replaced = true;
						break;
					}
				}
			}
		} else {
			contentPlaceholder.appendChild(contentNode);
		}
		*/
		
		const childNode = getValidChildNode(contentPlaceholder);
		if (childNode === null) {
			contentPlaceholder.appendChild(contentNode);
		} else {
			contentPlaceholder.replaceChild(contentNode, childNode);
		}
		
	} catch (e) {
		contentPlaceholder.appendChild(contentNode);
	}

	console.info("replaced: ", replaced);

	return contentNode;	
}

export function createHTMLFragment(htmlText, fragmentId) {
	let fragmentText = document.createElement("body");
	fragmentText.innerHTML = htmlText;
	
	let fragment = fragmentText.querySelector(`#${fragmentId}`);
	
	return document.importNode(fragment, true);
}

export function createHTMLFragmentFromRole(htmlText, role) {
	let fragmentText = document.createElement("body");
	fragmentText.innerHTML = htmlText;
	
	let fragment = getValidChildNode(fragmentText.querySelectorAll(`[role="${role}"]`)[0]);  
	
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


export function pushFragment(url, placeHolderId) {
	let httpRequest = getSync(url);
	
	let contentPlaceholder = null;
	
	let placeholders = document.querySelectorAll('[role="placeholder"]');
	
	for (const placeholder of placeholders) {
		if (placeholder.getAttribute("placeholder-id") === placeHolderId) {
			contentPlaceholder = placeholder;
		}
	}
	
	const newFragment = createHTMLFragmentFromRole(httpRequest.responseText, 'placeholder');
	
	setContentFromFragment (newFragment, contentPlaceholder);
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

