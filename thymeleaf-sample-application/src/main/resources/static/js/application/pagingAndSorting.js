import * as app from './thysa-application.js';

class GridComponent {
	#app = null;
	#randomSuffix = null;
	#grid = null;
	#paginator = null;
	#form = null;
	#withPagination = true;
	
	constructor(grid, app) {
		this.#grid = grid;
		this.#app = app;
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
		let headers = header.querySelectorAll("th");
		
		for (const header of headers) {
			header.addEventListener("click", async () => {
				await this.#sort(header.getAttribute("sort-field"));
			});
		}		
	}
	
	async #updateSortedContent(httpRequest) {
		let tBody = this.#grid.getElementsByTagName('tbody')[0];

		const newTBody = this.#app.createHTMLFragmentFromTag(httpRequest.responseText, 'tbody');

		document.importNode(newTBody);

		this.#grid.getElementsByTagName('table')[0].removeChild(tBody);
		this.#grid.getElementsByTagName('table')[0].appendChild(newTBody);
		
		await this.#app.intecommunication.onDataChange();
	}
	
	async #sort(field) {
		this.#form.currentSortField.value = field;
		const httpRequest = await this.#app.postFormSync(this.#form, this.#form.getAttribute("action"));
		
		//console.info("Response: ", httpRequest.responseText);
		
		/* Header handling */
		const header = this.#grid.getElementsByTagName('thead')[0];
		const newHeader = this.#app.createHTMLFragmentFromTag(httpRequest.responseText, 'thead');
		await this.#applySortEvents(newHeader);
		this.#grid.getElementsByTagName('table')[0].replaceChild(newHeader, header);

		/* Content */
		await this.#updateSortedContent(httpRequest);
		
		/* form value */
		const newForm = app.createHTMLFragmentFromTag(httpRequest.responseText, 'form');
		this.#form.currentSortDirection.value = newForm.currentSortDirection.value;
	}
	
	async #initSorting() {
		let headers = this.#grid.querySelectorAll("th");
		
		for (const header of headers) {
			header.addEventListener("click", async () => {
				await this.#sort(header.getAttribute("sort-field"));
			});
		}
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
		const httpRequest = await this.#app.postFormSync(this.#form, this.#form.getAttribute("action"));
		
		await this.#updatePagedContent(httpRequest);
		
		await this.#applyPagingEvents();
		
		await this.#updateSortedContent(httpRequest);
	}
	
	async #initPaging() {
		await this.#applyPagingEvents();
	}
	

	
	async #updatePagedContent(httpRequest) {
		let nav = this.#paginator.getElementsByTagName('nav')[0];
		
		const newNav = this.#app.createHTMLFragmentFromTag(httpRequest.responseText, 'nav');
		
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
	
	static async getInstance(grid, app) {
		let gridComponent = new GridComponent(grid, app);
		
		await gridComponent.init();
		
		return gridComponent;
	}
}

(async () => {
	//let gridComponents = null;
	let paginators = null;
	
	let randomSuffixes = [];
	
	async function initPaging(paginator) {

				
	}
	
	 
	function paginatorRandomSuffix(paginator) {
		return paginator.querySelectorAll('[role="grid-paging"]')[0].getAttribute("random-suffix");
	}

	async function applySortEvents(sortForm, header, gridComponent) {
		let headers = header.querySelectorAll("th");
		
		for (const header of headers) {
			header.addEventListener("click", async () => {
				sort(sortForm, header.getAttribute("sort-field"), gridComponent);
			});
		}		
	}
	
	async function sort(form, field, grid) {
		form.currentSortField.value = field;
		const httpRequest = await app.postFormSync(form, form.getAttribute("action"));
		
//		console.info("Response: ", httpRequest.responseText);
		
		//TODO: Improve later
		//await app.setContent (httpRequest.responseText, gridComponent);
//		const contentPlaceHolder = gridComponent.querySelectorAll('[role="grid-content"]')[0];
		
		/* Header handling */
		const header = grid.getElementsByTagName('thead')[0];
		const newHeader = app.createHTMLFragmentFromTag(httpRequest.responseText, 'thead');
		await applySortEvents(form, newHeader, grid);
		grid.getElementsByTagName('table')[0].replaceChild(newHeader, header);
		
		/* Content handling */
		let tBody = grid.getElementsByTagName('tbody')[0];
		
		const newTBody = app.createHTMLFragmentFromTag(httpRequest.responseText, 'tbody');
		
		document.importNode(newTBody);
		
		grid.getElementsByTagName('table')[0].removeChild(tBody);
		grid.getElementsByTagName('table')[0].appendChild(newTBody);
		
		/* Paging handling */
		//TODO: Later
		
		/* Form handling */
		const newForm = app.createHTMLFragmentFromTag(httpRequest.responseText, 'form');
		form.currentSortDirection.value = newForm.currentSortDirection.value;
		
		await app.intecommunication.onDataChange();
	}
	
	function gridComponentRandomSuffix(gridComponent) {
		return gridComponent.querySelectorAll('[role="grid-content"]')[0].getAttribute("random-suffix");
	}
	
	async function initSorting(grid) {
		const sortFormCollector = `[grid-form-id="grid-form-${gridComponentRandomSuffix(grid)}"]`;
		let sortForm = grid.querySelectorAll(sortFormCollector)[0];
		
		let headers = grid.querySelectorAll("th");

		for (const header of headers) {
			header.addEventListener("click", async () => {
				sort(sortForm, header.getAttribute("sort-field"), grid);
			});
		}
		
		randomSuffixes.push(grid.querySelectorAll('[role="grid-content"]')[0].getAttribute("random-suffix"));
	}
	
	async function init() {
		/* Grids */
		let grids = document.querySelectorAll('[role="grid-component"]');
		
		for (const grid of grids) {
//			await initSorting(grid);
			
			//console.info("random-suffix=", grid.querySelectorAll('[role="grid-content"]')[0].getAttribute("random-suffix"));
			
			await GridComponent.getInstance(grid, app);
		}
		
//		/* Paginators */
//		paginators = document.querySelectorAll('[role="grid-paging"]');
//		
//		for (const paginator of paginators) {
//			await initPaging(paginator);
//		}

		console.info("hash=", window.location);
	}
	
	await init();
})();