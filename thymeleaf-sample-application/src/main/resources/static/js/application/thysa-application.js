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


export async function postForm(form, uri) {
	const params = new FormData(form);
	let httpRequest = new XMLHttpRequest();
	httpRequest.open("POST", uri, false);
	httpRequest.send(params);
	
	return httpRequest;
}


/*
  ---------------------------
  -- POPULAR HTTP STATUSES --
  ---------------------------
*/

export const HttpStatus = Object.freeze({
	                   OK : Symbol(200),
	              CREATED : Symbol(201),
	          BAD_REQUEST : Symbol(400),
	INTERNAL_SERVER_ERROR : Symbol(500)
});


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
	  TABLE : Symbol("TABLE"),
   DATALIST : Symbol("DATALIST"),
     OPTION : Symbol("OPTION")
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

function getValidChildNodes(parentNode) {
	let validChildNodes = [];
	
	if (parentNode.hasChildNodes()) {
		for (const childNode of parentNode.childNodes) {
			if (childNode.nodeType === Node.ELEMENT_NODE || childNode.nodeType === Node.TEXT_NODE) {
				if (Object.keys(ValidChildNodePlaceholderTags).includes(childNode.tagName)) {
					validChildNodes.push (childNode);
				}
			}
		}
	}
	
	return validChildNodes;
}

export async function setContentFromText(htmlText, contentPlaceholder) {
	let contentNode = createNodeFromText(htmlText);

	return await setContentFromFragment(contentNode, contentPlaceholder);	
}

export async function setContentFromFragment(contentNode, contentPlaceholder) {
	try {
		const childNode = getValidChildNode(contentPlaceholder);
		if (childNode === null) {
			contentPlaceholder.appendChild(contentNode);
		} else {
			contentPlaceholder.replaceChild(contentNode, childNode);
		}
		
	} catch (e) {
		contentPlaceholder.appendChild(contentNode);
	}

	return contentNode;	
}

export async function replaceAllChildNodes(parentNode, newParentNode) {
	parentNode.replaceChildren(...newParentNode.childNodes);
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


export async function pushFragment(url, placeHolderId) {
	let httpRequest = getSync(url);
	
	let contentPlaceholder = null;
	
	let placeholders = document.querySelectorAll('[role="placeholder"]');
	
	for (const placeholder of placeholders) {
		if (placeholder.getAttribute("placeholder-id") === placeHolderId) {
			contentPlaceholder = placeholder;
			break;
		}
	}
	
	const newFragment = createHTMLFragmentFromRole(httpRequest.responseText, 'placeholder');
	
	await setContentFromFragment (newFragment, contentPlaceholder);
	
	return newFragment;
}

export async function pushFragmentFromHtmlText(htmlText, placeHolderId) {
	let contentPlaceholder = null;
	
	let placeholders = document.querySelectorAll('[role="placeholder"]');
	
	for (const placeholder of placeholders) {
		if (placeholder.getAttribute("placeholder-id") === placeHolderId) {
			contentPlaceholder = placeholder;
			break;
		}
	}
	
	const newFragment = createHTMLFragmentFromRole(htmlText, 'placeholder');
	
	await setContentFromFragment (newFragment, contentPlaceholder);
	
	return newFragment;
}


/*
  --------------------
  -- Grid Component -- 
  --------------------
*/

class GridComponent {
	#randomSuffix = null;
	#grid = null;
	#paginator = null;
	#form = null;
	#withPagination = true;
	
	constructor(grid) {
		this.#grid = grid;
	}
	
	async init() {
		/* Random Suffix */
		this.#randomSuffix = this.#grid.querySelectorAll('[role="grid-content"]')[0].getAttribute("random-suffix");
		
		/* Paginator */
		//TODO: Check if pagination is enable
		this.#paginator = this.#grid.querySelectorAll('[role="grid-paging"]')[0];
	
		/* Form */
		const formCollector = `[grid-form-id="grid-form-${this.#randomSuffix}"]`;
		this.#form = this.#grid.querySelectorAll(formCollector)[0];
				
		/* Sorting */
		await this.#initSorting();
		
		/* Filtering */
		await this.#initFiltering();
		
		/* Paging */
		if (this.#withPagination) {
			await this.#initPaging();
		}
	} 
	
	
	/* 
	  -------------
	  -- Sorting --
	  -------------
	*/
	
	async #applySortEvents(header) {
		let headers = header.querySelectorAll('[role="sort-column"]');
		
		for (const header of headers) {
			header.addEventListener("click", async () => {
				await this.#sort(header.getAttribute("sort-field"));
			});
		}		
	}
	
	async #updateSortedContent(httpRequest) {
		let tBody = this.#grid.getElementsByTagName('tbody')[0];

		const newTBody = createHTMLFragmentFromTag(httpRequest.responseText, 'tbody');

		document.importNode(newTBody);

		this.#grid.getElementsByTagName('table')[0].removeChild(tBody);
		this.#grid.getElementsByTagName('table')[0].appendChild(newTBody);
		
		await intecommunication.onDataChange(this.#form.sessionAttribute.value);
	}
	
	async #sort(field) {
		this.#form.currentSortField.value = field;
		const httpRequest = await postForm(this.#form, this.#form.getAttribute("action"));
		
		/* Header handling */
		const header = this.#grid.getElementsByTagName('thead')[0];
		const newHeader = createHTMLFragmentFromTag(httpRequest.responseText, 'thead');
		
		await this.#applySortEvents(newHeader);
		this.#grid.getElementsByTagName('table')[0].replaceChild(newHeader, header);

		/* Content */
		await this.#updateSortedContent(httpRequest);
		
		/* Form from HttpRequest */
		const formFromHttpRequest = createHTMLFragmentFromTag(httpRequest.responseText, 'form');
		
		/* Update Sorting Related values */
		this.#form.currentSortDirection.value = formFromHttpRequest.currentSortDirection.value;
		
		/* Update Filter Related values */
		let currentFilters = this.#grid.querySelectorAll('[role="filter"]');
		let filterValues = formFromHttpRequest.querySelectorAll('[role="filter-value"]');
		
		for (const filterValue of filterValues) {
			const index = filterValue.getAttribute("filter-value-index");
			for (const currentFilter of currentFilters) {
				if (currentFilter.getAttribute("filter-field-index") === index) {
					currentFilter.value = filterValue.value;
				}
			}		
		}
		
		/* Re-Apply Filtering events */
		this.#applyFilterEvents();
	}
	
	async #initSorting() {
		let headers = this.#grid.querySelectorAll('[role="sort-column"]');
		
		for (const header of headers) {
			header.addEventListener("click", async () => {
				await this.#sort(header.getAttribute("sort-field"));
			});
		}
	}

		
	/*
	  ---------------
	  -- Filtering --
	  ---------------
	*/

	async #applyFilterEvents() {
		let filters = this.#grid.querySelectorAll('[role="filter"]');

		for (const filter of filters) {
			filter.addEventListener("input", async () => {
				await this.#filter(filter.getAttribute("filter-field"), filter.value, filter.getAttribute("filter-field-index"));
			});
		}
	}
	
	async #filter(field, value, index) {
		console.info("filter:", "field=", field, "value=", value, "index=", index);
		
		const filterValueFieldName = `filters[${index}].filterValue`;
		
		this.#form[filterValueFieldName].value = value;
		
		const httpRequest = await postForm(this.#form, this.#form.getAttribute("action"));
		
		this.#updateSortedContent (httpRequest);
		
		this.#updatePagedContent(httpRequest);
		
		await this.#applyPagingEvents();
	}
	
	async #initFiltering() {
		await this.#applyFilterEvents();
	}
		
	
	/* 
	  ------------
	  -- Paging --
	  ------------
	*/
	
	async #applyPagingEvents() {
		let pageLinks = this.#paginator.querySelectorAll('[role="page-link"]');

		for (const pageLink of pageLinks) {
			pageLink.addEventListener("click", async () => {
				await this.#gotoToPage(pageLink.getAttribute("data-page-number"));
			});
		}
	}
	
	async #gotoToPage(pageNumber) {
		this.#form.pageNumber.value = pageNumber;
		const httpRequest = await postForm(this.#form, this.#form.getAttribute("action"));
		
		await this.#updatePagedContent(httpRequest);
		
		await this.#applyPagingEvents();
		
		await this.#updateSortedContent(httpRequest);
	}
	
	async #initPaging() {
		await this.#applyPagingEvents();
	}
	
	async #updatePagedContent(httpRequest) {
		let nav = this.#paginator.getElementsByTagName('nav')[0];
		
		const newNav = createHTMLFragmentFromTag(httpRequest.responseText, 'nav');
		
		document.importNode(newNav);
		
		this.#paginator.removeChild(nav);
		this.#paginator.appendChild(newNav);
	}
	
	get randomSuffix() {
		return this.#randomSuffix;
	}
	
	get grid() {
		return this.#grid;
	}
	
	get paginator() {
		return this.#paginator;
	}
	
	static async getInstance(grid) {
		let gridComponent = new GridComponent(grid);
		
		await gridComponent.init();
		
		return gridComponent;
	}
}

export let pagingAndSorting = async() => {
	async function init() {
		/* Grids */
		let grids = document.querySelectorAll('[role="grid-component"]');
		
		for (const grid of grids) {
			await GridComponent.getInstance(grid);
		}
	}

	await init();	
};

export let pagingAndSortingBySessionAttribute = async(sessionAttribute) => {
	async function init() {
		const placeholders = document.querySelectorAll('[role="placeholder"]');
		let gridPlaceholder = null; 
		
		for (const placeholder of placeholders) {
			if (placeholder.getAttribute("placeholder-id") === sessionAttribute) {
				gridPlaceholder = placeholder;
				break;
			}
		}
		
		if (gridPlaceholder != null) {
			/* Grids */
			let grids = gridPlaceholder.querySelectorAll('[role="grid-component"]');
			
			if (grids.length > 0) {
				await GridComponent.getInstance(grids[0]);
			}
		}
	}

	await init();	
};


/*
  ---------------------
  -- Form Components -- 
  ---------------------
*/

export async function formComponents() {
	async function initDatalistInputEvents() {
		const dataListInputElements = document.querySelectorAll('[role="datalist-input"]');
		
		for (const dataListInputElement of dataListInputElements) {
			dataListInputElement.addEventListener("input", async () => {
				await intecommunication.onDataListInput (
					{
					"sessionAttribute": dataListInputElement.getAttribute("session-attribute"),
					            "name": dataListInputElement.name,
					           "value": dataListInputElement.value,
				        "randomSuffix": dataListInputElement.getAttribute("random-suffix"),
						  "dataListId": `${dataListInputElement.id}_datalist`
						
					}
				);
			});
			
			dataListInputElement.addEventListener("change", async () => {
				/* User selects option */
				if (isOptionValue(dataListInputElement.value, `${dataListInputElement.id}_datalist`)) {
					document.getElementById(`${dataListInputElement.id}_hidden`).value = getOptionDataByInputValue(dataListInputElement.value, `${dataListInputElement.id}_datalist`);
					
					await intecommunication.onDataListSelectionChange (
						{
						"sessionAttribute" : dataListInputElement.getAttribute("session-attribute"),
						              "id" : dataListInputElement.id,
						           "value" :  document.getElementById(`${dataListInputElement.id}_hidden`).value
						}
					);
				}
			});
		}

		/* In case of fields rerendering due to user input error, take value of Hidden Element (is binded with Back-End object)  and put it in to Datalist Input Element  */		
		const dataListHiddenElements = document.querySelectorAll('[role="datalist-hidden"]');
		
		for (const dataListHiddenElement of dataListHiddenElements) {
			dataListHiddenElement.addEventListener("change", async () => {
				document.getElementById(dataListHiddenElement.getAttribute("text-input-id")).value = getOptionValueByInputData(dataListHiddenElement.value, `${dataListHiddenElement.getAttribute('text-input-id')}_datalist`);
			});
		}
	}
	
	function isOptionValue(value, datalistId) {
		const options = document.getElementById(datalistId).getElementsByTagName("option");
		
		for (const option of options) {
			if (option.value === value) {
				return true;
			}
		}
		
		return false;
	}
	
	function getOptionDataByInputValue(value, datalistId) {
		const options = document.getElementById(datalistId).getElementsByTagName("option");

		for (const option of options) {
			if (option.value === value) {
				return option.getAttribute("data");
			}
		}

		return null;
	}
	
	function getOptionValueByInputData(data, datalistId) {
		const options = document.getElementById(datalistId).getElementsByTagName("option");

		for (const option of options) {
			if (option.getAttribute("data") === data) {
				return option.value;
			}
		}

		return null;
	}
	
	async function initSelectEvents() {
		const selectElements = document.querySelectorAll('[role="select"]');
		
		for (const selectElement of selectElements) {
			selectElement.addEventListener("change", async () => {
				await intecommunication.onSelectionChange (
					{
						"sessionAttribute" : selectElement.getAttribute("session-attribute"),
						              "id" : selectElement.id,
						           "value" : selectElement.value,
								    "name" : selectElement.name,
						   "onChangeEvent" : selectElement.getAttribute("onchangeevent"),
						    "randomSuffix" : selectElement.getAttribute("random-suffix"),
							      "update" : selectElement.getAttribute("update")
					}
				);
			});
		}
	}
	
	async function initCalendarEvents() {
		const calendarElements = document.querySelectorAll('[role="calendar"]');
		
		for (const calendarElement of calendarElements) {
			calendarElement.addEventListener("change", async () => {
				console.info("date=", calendarElement.value);
			});
		}
		
		
	}
	
	async function init() {
		await initDatalistInputEvents();

		await initSelectEvents();
		
		await initCalendarEvents();
	}
	
	await init();
	
	return {
		isOptionValue
	}
};



/*
  ---------------
  -- Filtering --
  ---------------
*/

/*
export let filtering = async() => {
	async function init() {
		let filters = document.querySelectorAll('[role="filter"]');
		
		for (const filter of filters) {
			filter.addEventListener("input", async () => {
				console.info("value=", filter.value);
			});
		}
		
	}
	
	await init();
};
*/


/*
  -----------------------
  -- Intecommunication -- 
  -----------------------
*/

class Intecommunication {
	constructor() {
		//this.onDataChange = async () => {};
		
		this.onDataChange = async (data) => {};
		
		this.onPushFragment = async (data) => {};
		
		this.onPopFragment = async (data) => {};
		
		this.onDataListInput = async (data) => {};
		
		this.onDataListSelectionChange = async (data) => {};
		
		this.onSelectionChange = async (data) => {};
	}
}

export let intecommunication = new Intecommunication();

