<template>
  <div class="layout">
    <div class="container">
      <slot></slot>
    </div>
  </div>
  <canvas ref="canvas" id="canvas"></canvas>
</template>

<script setup>
import { onMounted, ref, useTemplateRef } from 'vue';
import { debounce } from '@/utils/common';
import { createStar } from '@/objects/star';

const canvasRef = useTemplateRef('canvas');
const canvasContext = ref(null);
let starCanvas, starContext, gradient;
let stars = [];

const width = ref(window.innerWidth);
const height = ref(window.innerHeight);
const maxStars = 1400;

onMounted(() => {
  initConst();
  initCanvas();
  initStars();
  // animation();
});

const onResizeHandler = () => {
  width.value = window.innerWidth;
  height.value = window.innerHeight;

  initCanvas();
  initStars();
  // animation();
};

window.addEventListener('resize', debounce(onResizeHandler, 1000));

const initConst = () => {
  starCanvas = document.createElement('canvas');
  starContext = starCanvas.getContext('2d');
  starCanvas.width = 100;
  starCanvas.height = 100;

  const half = starCanvas.width / 2;
  gradient = starContext.createRadialGradient(half, half, 0, half, half, half);
  gradient.addColorStop(0.025, '#fff');
  gradient.addColorStop(0.1, `hsl(217, 61%, 33%)`);
  gradient.addColorStop(0.25, `hsl(217, 64%, 6%)`);
  gradient.addColorStop(1, 'transparent');

  starContext.fillStyle = gradient;
  starContext.beginPath();
  starContext.arc(half, half, half, 0, Math.PI * 2);
  starContext.fill();
};

const initCanvas = () => {
  const canvas = canvasRef.value;
  canvas.width = width.value;
  canvas.height = height.value;
  canvasContext.value = canvas.getContext('2d');
};

const initStars = () => {
  stars = Array(maxStars)
    .fill(0)
    .map(() =>
      createStar(
        width.value,
        height.value,
        maxStars,
        canvasContext,
        starCanvas,
      ),
    );
};

const animation = () => {
  canvasContext.value.globalCompositeOperation = 'source-over';
  canvasContext.value.globalAlpha = 0.8;
  canvasContext.value.fillStyle = `hsla(217, 64%, 6%, 1)`;
  canvasContext.value.fillRect(0, 0, width.value, height.value);

  canvasContext.value.globalCompositeOperation = 'lighter';

  for (const star of stars) {
    star.draw();
  }

  requestAnimationFrame(animation);
};
</script>

<style>
.layout {
  /* remove scroll width */
  position: absolute;
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
