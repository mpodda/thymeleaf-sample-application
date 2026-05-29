import {AdminBase as AdminBase} from './AdminBase.js';

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

	async init() {
		await this.#initEvents(); 
	}

	static async getInstance() {
		let countriesAdmin = new CountriesAdmin();
		
		await countriesAdmin.init();
		
		return countriesAdmin;
	}
	
	
}