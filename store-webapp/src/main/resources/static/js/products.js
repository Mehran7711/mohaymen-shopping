document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (pageNo) => ({
        pageNo: pageNo,
        products: {
            data: []
        },
        init() {
            this.loadProducts(this.pageNo);
            // updateCartItemCount();
        },
        loadProducts(pageNo) {
            $.ajax({
                url: "/api/products?page=" + pageNo,
                success: (resp) => {
                    this.products = resp;
                },
                error: (xhr) => {
                    if (xhr.status === 401) {
                        alert("You should login.");
                    }
                }
            });
        },
        addToCart(product) {
            addProductToCart(product).then(r => console.log("Result is: " + r));
        }
    }))
});