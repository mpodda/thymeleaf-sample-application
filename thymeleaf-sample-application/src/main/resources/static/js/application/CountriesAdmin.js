import {AdminBase as AdminBase} from './AdminBase.js';

import * as app from './thysa-application.js';

export class CountriesAdmin extends AdminBase {
	constructor () {
		super();
		
		this.SessionAttribute = "countries";
		
		this.AddValueUrl = "/new-country";
		this.ListValueUrl = "/list-countries";
		this.SaveValueUrl = "/save-country";
		this.EditValueUrl = "/edit-country?countryId";
	}
	
	
	async initGridEvents() {
		/* Edit Countries */
		const editCountryButtons = document.querySelectorAll('[role="edit-country"]');

		for (const editCountryButton of editCountryButtons) {
			editCountryButton.addEventListener("click", async () => {
				const countryId = editCountryButton.getAttribute("data-id");
				await this.editValue(countryId);
			});
		}
	}
	
	async #initEvents() {
		/* Add Country */
		const addCountryButton = document.querySelectorAll('[role="add-country"]')[0];
		addCountryButton.addEventListener("click", async () => {
			await this.addValue();
		});

		await this.initGridEvents();
	}

	async filter(data) {
//		console.info("filter:", data);
		
		switch (data.name) {
			case "continent_input" : 
				 //TODO: Call
//				 console.info("Call filter for continent", data);
				 
				 const filterForms = document.querySelectorAll('[role="filter-form"]');
				 
				 for (let filterForm of filterForms) {
					if (filterForm.getAttribute("filter-form-id") === `filter-form-${data.randomSuffix}`) {
						//console.info("filterForm=", filterForm);
						
						//filterForm.action = `${contextPath}filter-continents`;
						filterForm.sessionAttribute.value = data.sessionAttribute;
						filterForm.name.value = data.name;
						filterForm.value.value = data.value;
						filterForm.randomSuffix.value = data.randomSuffix;
						
						const httpRequest = await app.postForm(filterForm, `${contextPath}filter-continents`);
						
						const newDataListFragment = app.createHTMLFragment(httpRequest.responseText, data.dataListId);
						
						const parentNode = document.getElementById(data.dataListId);
						
						await app.replaceAllChildNodes(parentNode, newDataListFragment);
						
						break;
					}
				 }
				 
				 
				 //let httpRequest = app.getSync(`/filter-continents?continentName=${data.value}`);
				 
				 //console.info("response=", httpRequest.responseText);
				 
			break;
			default: break;
		}
		
	}
	
	async init() {
		await this.#initEvents(); 
	}
	

	static async getInstance() {
		let countriesAdmin = new CountriesAdmin();
		
		await countriesAdmin.init();
		
		return countriesAdmin;
	}
	
	
}