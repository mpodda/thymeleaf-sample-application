//import {intecommunication as app_itercom} from './thysa-application.js';

import {ContinentsAdmin as ContinentsAdmin} from './ContinentsAdmin.js';
import {PersonsAdmin as PersonsAdmin} from './PersonsAdmin.js';

(async () => {
//	let intercom = app_itercom;
	
//	async function initGridEvents() {
//		/* Edit Continents */
//		const editCondinentButtons = document.querySelectorAll('[role="edit-continent"]');
//
//		for (const editCondinentButton of editCondinentButtons) {
//			editCondinentButton.addEventListener("click", async () => {
//				const condinentId = editCondinentButton.getAttribute("data-id");
//
//				console.info("Edit Continent with id", condinentId);				
//			});
//		}	

//		const editPersonButtons = document.querySelectorAll('[role="edit-person"]');
//
//		for (const editPersonButton of editPersonButtons) {
//			editPersonButton.addEventListener("click", async () => {
//				const personId = editPersonButton.getAttribute("data-id");
//				
//				console.info(`Edit person with id ${personId}`);
//			});
//		}		
//	} 
	
//	async function initEvents() {
//		await ContinentsAdmin.getInstance();
//		
//		await PersonsAdmin.getInstance();
//		
//		/* Add Continent */
////		console.info(document.querySelectorAll('[role="add-continent"]').length, "add continent buttons found");
//		
////		const addContinentButton = document.querySelectorAll('[role="add-continent"]')[0];
////		addContinentButton.addEventListener("click", async () => {
////			console.info("Add Continent");
////		});
//		
//		//await initGridEvents();
//		
//		 
//	} 
	
//	intercom.onDataChange = async() => {
//		await initGridEvents();
//	}

	async function init() {
//		await initEvents();

		await ContinentsAdmin.getInstance();
		await PersonsAdmin.getInstance();
	}
	
	await init();
})();