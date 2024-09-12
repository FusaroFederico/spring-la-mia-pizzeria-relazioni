/*************
JS strict mode
*************/
'use strict';

const pizzaPrice = document.getElementById('pizza-price');

// input price warning
pizzaPrice.addEventListener("input", function(){
	let price = Number(pizzaPrice.value);
	
	if (price < 0.01 || price.toFixed(2) != price){
		document.getElementById('invalid-price').classList.remove('d-none');
	} else {
		document.getElementById('invalid-price').classList.add('d-none');
	}
});