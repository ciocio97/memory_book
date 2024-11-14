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

const canvasRef = useTemplateRef('canvas');
const canvasContext = ref(null);

const width = ref(0);
const height = ref(0);
const maxCount = 1400;
const stars = [];

const starCanvas = document.createElement('canvas');
const starContext = starCanvas.getContext('2d');
starContext.width = 100;
starContext.height = 100;

class Star {
  constructor(width, height) {
    this.orbitRadius = random(maxOrbit(width, height));
    this.radius = random(60, this.orbitRadius) / 12;
    this.orbitX = width / 2;
    this.orbitY = height / 2;
    this.timePassed = random(0, maxCount);
    this.speed = random(this.orbitRadius) / 50000;
    this.alpha = random(2, 10) / 10; // 0.5 ~ 1.1
  }

  draw(ctx) {
    const x = Math.sin(this.timePassed) * this.orbitRadius + this.orbitX;
    const y = Math.cos(this.timePassed) * this.orbitRadius + this.orbitY;
    const twinkle = random(10); // 0 ~ 10

    // if (twinkle === 1 && this.alpha > 0) {
    //   this.alpha -= 0.05;
    // } else if (twinkle === 2 && this.alpha < 1) {
    //   this.alpha += 0.05;
    // }

    const dx = x - this.radius / 2;
    const dy = y - this.radius / 2;
    const dWidth = this.radius;
    const dHeight = this.radius;

    ctx.globalAlpha = this.alpha;
    ctx.drawImage(starCanvas, dx, dy, dWidth, dHeight);
    this.timePassed += this.speed;
  }
}

const setBackground = () => {
  // create stars
  for (let i = 0; i < maxCount; i++) {
    stars.push(new Star(width.value, height.value));
  }

  animation();
};

const random = (min, max) => {
  let minVal = min;
  let maxVal = max;

  // min: 0, max: argument
  if (max === undefined) {
    maxVal = min;
    minVal = 0;
  }

  // max min align
  if (min > max) {
    const tmp = max;
    maxVal = min;
    minVal = tmp;
  }

  // random number + min
  return Math.floor(Math.random() * (maxVal - minVal + 1)) + minVal;
};

const maxOrbit = (x, y) => {
  const max = Math.max(x, y);
  // a^2 + b^2 = c^2
  const diameter = Math.round(Math.sqrt(max * max + max * max));

  return diameter / 2;
};

const animation = () => {
  const ctx = canvasContext.value;

  ctx.globalCompositeOperation = 'source-over';
  ctx.globalAlpha = 0.8;
  ctx.fillStyle = 'hsla(' + 217 + ', 64%, 6%, 1)';
  ctx.fillRect(0, 0, width.value, height.value);

  ctx.globalCompositeOperation = 'lighter';

  for (let star of stars) {
    star.draw(ctx);
  }

  window.requestAnimationFrame(animation);
};

onMounted(() => {
  width.value = window.innerWidth;
  height.value = window.innerHeight;

  const canvas = canvasRef.value;
  canvas.width = width.value;
  canvas.height = height.value;
  canvasContext.value = canvas.getContext('2d');

  setBackground();

  const half = starCanvas.width / 2;
  const gradient = starContext.createRadialGradient(
    half,
    half,
    0,
    half,
    half,
    half,
  );
  gradient.addColorStop(0.025, '#fff');
  gradient.addColorStop(0.1, 'hsl(' + 217 + ', 61%, 33%)');
  gradient.addColorStop(0.25, 'hsl(' + 217 + ', 64%, 6%)');
  gradient.addColorStop(1, 'transparent');
});
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
