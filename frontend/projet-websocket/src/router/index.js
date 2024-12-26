import { createRouter, createWebHistory } from "vue-router";

import ListingsComponent from "@/components/ListingsComponent.vue";
import InboxComponent from "@/components/InboxComponent.vue";

const routes = [
    { path: "/", name: "ListingsComponent", component: ListingsComponent },
    { path: "/inbox", name: "InboxComponent", component: InboxComponent },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;