<template>
    <div class="layout">
        <div class="container">
            <slot></slot>
        </div>
    </div>
</template>

<script>
export default {
    name: "TheLayout",
}
</script>

<style>
.layout {
    /* remove scroll width */
    width: calc(100vw - 2px);
    height: calc(100vh - 2px);
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1px solid;

    .container {
        max-width: 320px;
        max-height: calc(100vh - 100px);
        overflow: hidden;
        border: 1px solid;
    }
}
</style>