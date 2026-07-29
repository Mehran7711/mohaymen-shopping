const STORE_STATE_KEY = "STORE_STATE";

// const getCart = function() {
//     let cart = localStorage.getItem(STORE_STATE_KEY)
//     if (!cart) {
//         cart = JSON.stringify({items:[], totalAmount:0 });
//         localStorage.setItem(STORE_STATE_KEY, cart)
//     }
//     return JSON.parse(cart)
// }

async function getCart() {

    const response = await fetch("http://localhost:8989/store/api/cart");

    if (!response.ok)
        throw new Error("Cannot load cart");

    return await response.json();
}

// const addProductToCart = function(product) {
//     let cart = getCart();
//     let cartItem = cart.items.find(itemModel => itemModel.code === product.code);
//     if (cartItem) {
//         cartItem.quantity = parseInt(cartItem.quantity) + 1;
//     } else {
//         cart.items.push(Object.assign({}, product, {quantity: 1}));
//     }
//     localStorage.setItem(STORE_STATE_KEY, JSON.stringify(cart));
//     updateCartItemCount();
// }

async function addProductToCart(product) {

    console.log("enter: " + product.id);
    $.ajax({
        url: "/cart/items",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            productId: product.id,
            quantity: 1
        }),
        success: function () {
            console.log("Added to cart");
        }
    });


}

// const updateProductQuantity = function(code, quantity) {
//     let cart = getCart();
//     if(quantity < 1) {
//         cart.items = cart.items.filter(itemModel => itemModel.code !== code);
//     } else {
//         let cartItem = cart.items.find(itemModel => itemModel.code === code);
//         if (cartItem) {
//             cartItem.quantity = parseInt(quantity);
//         } else {
//             console.log("Product code is not already in Cart, ignoring")
//         }
//     }
//     localStorage.setItem(STORE_STATE_KEY, JSON.stringify(cart));
//     updateCartItemCount();
// }
async function updateProductQuantity(cartItemId, quantity) {

    if (quantity <= 0) {

        return deleteCartItem(cartItemId);
    }

    await fetch(`/api/cart/items/${cartItemId}`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            quantity: quantity
        })
    });

    updateCartItemCount();
}

async function deleteCartItem(cartItemId) {

    await fetch(`/api/cart/items/${cartItemId}`, {

        method: "DELETE"
    });

    updateCartItemCount();
}

const deleteCart = function () {
    localStorage.removeItem(STORE_STATE_KEY)
    updateCartItemCount();
}

async function updateCartItemCount() {
    const cart = await getCart();
    let count = 0;
    cart.items.forEach(item => {
        count = count + item.quantity;
    });
    $('#cart-item-count').text('(' + count + ')');
}

// function getCartTotal() {
//     let cart = getCart();
//     let totalAmount = 0;
//     cart.items.forEach(item => {
//         totalAmount = totalAmount + (item.price * item.quantity);
//     });
//     return totalAmount;
// }
async function getCartTotal() {

    const cart = await getCart();

    return cart.totalAmount;
}