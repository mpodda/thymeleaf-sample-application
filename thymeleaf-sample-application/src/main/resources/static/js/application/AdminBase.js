import {intecommunication as app_itercom, postForm} from './thysa-application.js';
import {pushFragment as pushFragment, createHTMLFragmentFromRole, setContentFromFragment, createHTMLFragment} from './thysa-application.js';
import {pagingAndSortingBySessionAttribute as pagingAndSortingBySessionAttribute} from './thysa-application.js';
import {HttpStatus}  from './thysa-application.js';
import {formComponents as formComponents}  from './thysa-application.js';


export class AdminBase {
	static currentInstances = [];
	
	#sessionAttribute = null;
	
	#addValueUrl = null;
	#editValueUrl = null;
	#listValueUrl = null;
	#saveValueUrl = null;
	
	#formComponents = null;
	
	#addValueRole = null;
	#editValueRole = null;
	
	constructor() {
		AdminBase.currentInstances.push(this);
		
		app_itercom.onDataChange = async(sessionAttribute) => {
			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === sessionAttribute) {
					await adminBaseInstance.initGridEvents();
				}
			}
		};
		
		app_itercom.onPopFragment = async (sessionAttribute) => {
			await pagingAndSortingBySessionAttribute(sessionAttribute);

			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === sessionAttribute) {
					await adminBaseInstance.init();
				}
			}
		}
		
		app_itercom.onPushFragment = async (sessionAttribute) => {
			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === sessionAttribute) {
					adminBaseInstance.FormComponents = await formComponents();
				}
			}
		}
		
		app_itercom.onDataListInput = async (data) => {
			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === data.sessionAttribute) {
					if (!adminBaseInstance.FormComponents.isOptionValue(data.value, data.dataListId)) {
						await adminBaseInstance.filter(data);
					}
				}
			}
		}
		
		app_itercom.onDataListSelectionChange = async (data) => {
			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === data.sessionAttribute) {
					await adminBaseInstance.dataListSelectionChange(data);
				}
			}
		}
		
		app_itercom.onSelectionChange = async (data) => {
			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === data.sessionAttribute) {
					await adminBaseInstance.selectionChange(data);
				}
			}
		};
	}
	
	/** 
	 * Rerenders form fields. Usually in case of User Input validation errors
	 */
	async #pushFormFields(htmlText) {
		let currentContentPlaceholder = null;
		
		let placeholders = document.querySelectorAll('[role="form-content"]');
		
		for (const placeholder of placeholders) {
			if (placeholder.getAttribute("form-content-id") === this.#sessionAttribute) {
				currentContentPlaceholder = placeholder;
				break;
			}
		}		
		
		const fieldsFragment = createHTMLFragmentFromRole(htmlText, 'form-content');
		
		await setContentFromFragment (fieldsFragment, currentContentPlaceholder);
		
		/* Re-init form fields events, etc. */
		await formComponents();
		
		/* 
		Collect all datalist related hidden fields (contain binding data) and dispatch event for each of them 
		in order to catch it the listener and put the corresponding value in to the 
		Datalist related text input element 
		*/
		const dataListHiddenElements = fieldsFragment.querySelectorAll('[role="datalist-hidden"]');
		
		for (const dataListHiddenElement of dataListHiddenElements) {
			dataListHiddenElement.dispatchEvent(new Event("change"));
		}
	}
	
	async #initSaveEvent(containerFragment) {
		const saveButton = containerFragment.querySelector("#btnSave");
		
		saveButton.addEventListener("click", async () => {
			const forms = containerFragment.getElementsByTagName("form");
			
			for (const form of forms) {
				if (form.getAttribute("role") === "form") {
					const httpRequest = await postForm(form, this.#saveValueUrl);
					
					if (httpRequest.status === Number (HttpStatus.OK.description)) {
						await pushFragment(this.#listValueUrl, this.#sessionAttribute);

						//await app_itercom.onDataChange(this.#sessionAttribute);

						await app_itercom.onPopFragment(this.#sessionAttribute);
					} else {
						await this.#pushFormFields(httpRequest.responseText);						
					}
					
					break;
				}
			}
			
			console.info("Save");
		});		
	}
	
	async #initCancelEvent(containerFragment) {
		const cancelButton = containerFragment.querySelector("#btnCancel");
		
		cancelButton.addEventListener("click", async () => {

			await pushFragment(this.#listValueUrl, this.#sessionAttribute);
			
			//await app_itercom.onDataChange(this.#sessionAttribute);
			
			await app_itercom.onPopFragment(this.#sessionAttribute);
			
		 	console.info("Cancel");
		});		
	}
	
	async initEvents() {}
	
	async filter(data) {}
	
	async dataListSelectionChange(data) {
		console.info("AdminBase::dataListSelectionChange",data);
	}
	
	async selectionChange(data) {
//		console.info("AdminBase", "selectionChange", "data=", data);
		
		if (data.onChangeEvent != null) {  
			const valueChangeForm = document.querySelectorAll('[role="value-change-form"]')[0];
			
			valueChangeForm.value.value = data.value;
			valueChangeForm.name.value = data.name;
			valueChangeForm.event.value = data.onChangeEvent;
			valueChangeForm.randomSuffix.value = data.randomSuffix;
			
			const httpRequest = await postForm(valueChangeForm, data.onChangeEvent);
			
			const currentSelectElement = document.getElementById(data.update);
			
			const newSelectElement = createHTMLFragment(httpRequest.responseText, data.update);
			
			for (let i = currentSelectElement.options.length; ;i--){
				currentSelectElement.options[i] = null;
				if (i==0) {
					break;
				}
			}
			
			const newLength = newSelectElement.options.length;
			
			for (let i = 0; i < newLength; i++) {
				currentSelectElement[i] = newSelectElement.options[0];
			}
			
			if (currentSelectElement.options.length > 0){
				currentSelectElement.options[0].selected = true;
			}
			 
		} else {
			console.info("No onChangeEvent");
		}
	}
	
	async initGridEvents() {
		/* Add value */
		const addValueButton = document.querySelectorAll(`[role="${this.#addValueRole}"]`)[0];
		addValueButton.addEventListener("click", async () => {
			await this.addValue();
		});
		
		
		/* Edit value */
		const editValueButtons = document.querySelectorAll(`[role="${this.#editValueRole}"]`);
		
		for (const editValueButton of editValueButtons) {
			editValueButton.addEventListener("click", async () => {
				const valueId = editValueButton.getAttribute("data-id");
				await this.editValue(valueId);
			});
		}
	}	
	
	async init() {
		await this.initGridEvents();
		
		//await this.initEvents();
	}
	
	async editValue(id) {
		console.info("Edit Value with id", id);
		
		const formFragment = await pushFragment(`${this.#editValueUrl}=${id}`, this.#sessionAttribute);
		
		/* Save */
		await this.#initSaveEvent(formFragment);

		/* Cancel */
		await this.#initCancelEvent(formFragment);
		
		await app_itercom.onPushFragment(this.#sessionAttribute);
	}
	
	async addValue() {
		//console.info("Add value", "addValueUrl=", this.#addValueUrl, "sessionAttribute=", this.#sessionAttribute);
		console.info("Add value");
		
		const formFragment = await pushFragment(this.#addValueUrl, this.#sessionAttribute);

		/* Save */
		await this.#initSaveEvent(formFragment);
		
		
		/* Cancel */
		await this.#initCancelEvent(formFragment);
		
		await app_itercom.onPushFragment(this.#sessionAttribute);
	}
	
	get SessionAttribute() {
		return this.#sessionAttribute;
	}
	
	set SessionAttribute(value) {
		this.#sessionAttribute = value;
	}

	get AddValueUrl() {
		return this.#addValueUrl;
	}
	
	set AddValueUrl(value) {
		this.#addValueUrl = value;
	}
	
	get EditValueUrl() {
		return this.#editValueUrl;
	}
	
	set EditValueUrl(value) {
		this.#editValueUrl = value;
	}

	get ListValueUrl() {
		return this.#listValueUrl;
	}
	
	set ListValueUrl(value) {
		this.#listValueUrl = value;
	}
	
	get SaveValueUrl () {
		return this.#saveValueUrl;
	}
	
	set SaveValueUrl(value) {
		this.#saveValueUrl = value;
	}
	
	get FormComponents() {
		return this.#formComponents;
	}
	
	set FormComponents(value) {
		this.#formComponents = value;
	}
	
	get AddValueRole() {
		return this.#addValueRole;
	}
	
	set AddValueRole(value) {
		this.#addValueRole = value;
	}
	
	get EditValueRole() {
		return this.#editValueRole;
	}
	
	set EditValueRole(value) {
		this.#editValueRole = value;
	}
}