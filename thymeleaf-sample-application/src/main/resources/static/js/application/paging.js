import * as app from './thysa-application.js';

(async () => {
	let paginators = null;
	let randomSuffixes = [];
	
	async function initPaging() {
			
	}
	
	async function init() {
		paginators = document.querySelectorAll('[role="grid-paging"]');
	}
	
	await init();
})();