import * as app from './thysa-application.js';

(async () => {
	async function sort(randomSuffix, headerElement, form, formElementId, uri) {
		const field = headerElement.id.replace (`_${randomSuffix}`, '');
		form.currentSortField.value = field;
		const httpRequest = await app.postFormSync(formElementId, uri);
		let newNode = await app.setContent (httpRequest.responseText, "continents-grid");
		
		reInitSorting (randomSuffix, newNode);
	} 
	 
	async function reInitSorting(randomSuffix, node) {
		let headers = node.querySelector(`#head_${randomSuffix}`).querySelectorAll("th");
		
		const formElementId = `sort_form_${randomSuffix}`;
		const form =  document.querySelector(`#${formElementId}`);
		const uri = form.getAttribute("action");

		for (let i=0; i< headers.length; i++) {
			headers[i].addEventListener("click", async () => {
				//const field = headers[i].id.replace (`_${randomSuffix}`, '');
				//form.currentSortField.value = field;
				
				//const httpRequest = await app.postFormSync(formElementId, uri);
				
				//console.info(httpRequest.responseText);
				
				//let newNode = await app.setContent (httpRequest.responseText, "continents-grid");
				
				headers[i].removeEventListener("click", sort);
				
				sort(randomSuffix, headers[i], form, formElementId, uri);
				
				
				
				//console.info ("this=", this);
				
				 
			});
		}
	}
	
	async function initSorting(randomSuffix) {
		console.info("initSorting for", randomSuffix, `head_${randomSuffix}`);
		
		let headers = document.getElementById(`head_${randomSuffix}`).querySelectorAll("th");
		
		//console.info(`${headers.length} header(s) found`);
		
		const formElementId = `sort_form_${randomSuffix}`;
		const form =  document.querySelector(`#${formElementId}`);
		
		form.randomSuffix.value = randomSuffix;

//		const field = headers[i].id.replace (`_${randomSuffix}`, '');
		
		for (let i=0; i< headers.length; i++) {
			headers[i].addEventListener("click", async () => {
				console.info("Sort field", headers[i].id, "column", i+1);
				
				//console.info("action", document.getElementById(`sort_form_${randomSuffix}`).getAttribute("action"));
				
//				const formElementId = `sort_form_${randomSuffix}`;
//				const form =  document.querySelector(`#${formElementId}`);

				const field = headers[i].id.replace (`_${randomSuffix}`, '');
				form.currentSortField.value = field;
				
				const uri = form.getAttribute("action");
				
				const httpRequest = await app.postFormSync(formElementId, uri);
				
				//console.info(httpRequest.responseText);
				
				let newNode = await app.setContent (httpRequest.responseText, "continents-grid");
				
				//console.info ("newNode=", newNode);
				await reInitSorting(randomSuffix, newNode);
				
				//let newNode = await initSorting(randomSuffix);
			});
		}
	}
	
	async function init() {
		const gridComponents = document.querySelectorAll('[role="grid-component"]');
		
		console.info(gridComponents.length, " gridComponents found");
		
		/*
		for (const gridComponent of gridComponents) {
			await initSorting(gridComponent.getAttribute("random-suffix"));
		}
		*/
	}

	
	await init();
})();