//import * as app from './thysa-application.js';

//import {dd as app_dd} from './thysa-application.js';

import {intecommunication as app_itercom} from './thysa-application.js';

(async () => {
	let intercom = app_itercom;
	
	intercom.onDataChange = async() => {
		const editCondinentButtons = document.querySelectorAll('[role="edit-continent"]');

		for (const editCondinentButton of editCondinentButtons) {
			editCondinentButton.addEventListener("click", async () => {
				const condinentId = editCondinentButton.getAttribute("data-id");
				
				console.info(`Edit condinent with id ${condinentId}`);
			});
		}	
		
		const editPersonButtons = document.querySelectorAll('[role="edit-person"]');

		for (const editPersonButton of editPersonButtons) {
			editPersonButton.addEventListener("click", async () => {
				const personId = editPersonButton.getAttribute("data-id");
				
				console.info(`Edit person with id ${personId}`);
			});
		}
	}
	
	/*
	let dd = app_dd;
	
	dd.ddw = () => {
		console.info("home onDataChange");
		
		const editCondinentButtons = document.querySelectorAll('[role="edit-continent"]');

		for (const editCondinentButton of editCondinentButtons) {
			editCondinentButton.addEventListener("click", async () => {
				const condinentId = editCondinentButton.getAttribute("data-id");
				
				console.info(`Edit condinent with id ${condinentId}`);
			});
		}		
	}
	*/
	


	async function init() {
		//console.info("Init home!");
		
		/*
		document.querySelector("#btnModel").addEventListener("click", async () => {
			console.info("Submit Model");
			
			app.getSync("/model");
		});
		*/
		const editCondinentButtons = document.querySelectorAll('[role="edit-continent"]');
		
		for (const editCondinentButton of editCondinentButtons) {
			editCondinentButton.addEventListener("click", async () => {
				const condinentId = editCondinentButton.getAttribute("data-id");
				
				console.info(`Edit condinent with id ${condinentId}`);
			});
		}
		
		const editPersonButtons = document.querySelectorAll('[role="edit-person"]');

		for (const editPersonButton of editPersonButtons) {
			editPersonButton.addEventListener("click", async () => {
				const condinentId = editPersonButton.getAttribute("data-id");
				
				console.info(`Edit person with id ${condinentId}`);
			});
		}		
	}
	
	await init();
})();