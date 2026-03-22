import * as app from './thysa-application.js';

(async () => {
	let gridComponents = null;
	let sortForm = null;
	
	async function applySortEvents(header, gridComponent) {
		let headers = header.querySelectorAll("th");
		
		for (const header of headers) {
			header.addEventListener("click", async () => {
				sort(sortForm, header.getAttribute("sort-field"), gridComponent);
			});
		}		
	}
	
	async function sort(form, field, gridComponent) {
		form.currentSortField.value = field;
		
		const httpRequest = await app.postFormSync(form, form.getAttribute("action"));
		
		//console.info("Response: ", httpRequest.responseText);
		//console.info("gridComponent=", gridComponent.childNodes[1]);
//		console.info("gridComponent=", gridComponent);
		
		//TODO: Improve later
		//await app.setContent (httpRequest.responseText, gridComponent);
//		const contentPlaceHolder = gridComponent.querySelectorAll('[role="grid-content"]')[0];
		
		/* Header handling */
		const header = gridComponent.getElementsByTagName('thead')[0];
		const newHeader = app.createHTMLFragmentFromTag(httpRequest.responseText, 'thead');
		await applySortEvents(newHeader, gridComponent);
		gridComponent.getElementsByTagName('table')[0].replaceChild(newHeader, header);
		
		/* Content handling */
		let tBody = gridComponent.getElementsByTagName('tbody')[0];
		
		const newTBody = app.createHTMLFragmentFromTag(httpRequest.responseText, 'tbody');
		
		document.importNode(newTBody);
		
		gridComponent.getElementsByTagName('table')[0].removeChild(tBody);
		gridComponent.getElementsByTagName('table')[0].appendChild(newTBody);
		
		/* Paging handling */
		
		/* Form handling */
		const newForm = app.createHTMLFragmentFromTag(httpRequest.responseText, 'form');
		form.currentSortDirection.value = newForm.currentSortDirection.value;
		
		await app.intecommunication.onDataChange();
	}
	 
	async function initSorting(gridComponent) {
		sortForm = gridComponent.querySelectorAll('[grid-form-type="sort-form"]')[0];
		
		let headers = gridComponent.querySelectorAll("th");

		for (const header of headers) {
			header.addEventListener("click", async () => {
				//console.info("sort by field", header.getAttribute("sort-field"), gridComponent);
				
				sort(sortForm, header.getAttribute("sort-field"), gridComponent);
			});
		}
	}
	
	async function init() {
		gridComponents = document.querySelectorAll('[role="grid-component"]');
		
		console.info(gridComponents.length, " gridComponents found");
		
		for (const gridComponent of gridComponents) {
			await initSorting(gridComponent);
		}
	}

	
	await init();
})();