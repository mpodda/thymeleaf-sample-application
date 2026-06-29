import {CitiesAdmin} from './CitiesAdmin.js';

(async () => {

		async function init() {
		await CitiesAdmin.getInstance();
	}
	
	await init();
})();