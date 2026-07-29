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
            // $.getJSON("http://localhost:8989/store/api/products?page=" + pageNo, (resp) => {
            //     console.log("Products Resp:", resp)
            //     this.products = resp;
            // });
            $.getJSON("/getProducts?page=" + pageNo, (resp) => {
                console.log(resp);
                this.products = resp;
            });
        },
        addToCart(product) {
            addProductToCart(product).then(r => console.log("Result is: " + r));
        }
    }))
});