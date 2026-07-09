import {PersonsAdmin} from './PersonsAdmin.js';

(async () => {

		async function init() {
		await PersonsAdmin.getInstance();
	}
	
	await init();
})();