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
		
		this.AddValueRole = "add-country";
		this.EditValueRole = "edit-country";

	}
	
	async initEvents() {
		console.info("Init events for Countries Admin");
	}


	async filter(data) {
		switch (data.name) {
			case "continent_input" : 
				 const filterForms = document.querySelectorAll('[role="filter-form"]');
				 
				 for (let filterForm of filterForms) {
					if (filterForm.getAttribute("filter-form-id") === `filter-form-${data.randomSuffix}`) {
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
			break;
			default: break;
		}
		
	}
	
//	async init() {
//		await this.#initEvents();
//	}
	

	static async getInstance() {
		let countriesAdmin = new CountriesAdmin();
		
		await countriesAdmin.init();
		
		return countriesAdmin;
	}
}