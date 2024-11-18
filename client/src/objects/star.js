const random = (min, max) => {
  // min: 0, max: argument
  if (!max) {
    max = min;
    min = 0;
  }

  // max min align
  if (min > max) {
    [min, max] = [max, min];
  }

  // random number + min
  return Math.floor(Math.random() * (max - min + 1)) + min;
};

// return max radius
const maxOrbit = (x, y) => {
  const max = Math.max(x, y);
  // a^2 + b^2 = c^2
  const diameter = Math.round(Math.sqrt(max * max + max * max));

  return diameter / 2;
};

// Create star function
const createStar = (width, height, maxStars, canvasContext, starCanvas) => {
  const orbitLength = maxOrbit(width, height);
  const star = {
    orbitX: width / 2,
    orbitY: height / 2,
    orbitRadius: random(orbitLength),
    starRadius: random(60, orbitLength) / 12,
    timePassed: random(maxStars),
    speed: random(orbitLength) / 200000,
    alpha: random(2, 10) / 10,
    draw() {
      // 1, 2, 3, 4 quadrant
      const x = Math.sin(this.timePassed) * this.orbitRadius + this.orbitX;
      const y = Math.cos(this.timePassed) * this.orbitRadius + this.orbitY;

      const image = starCanvas;
      const dx = x - this.starRadius / 2;
      const dy = y - this.starRadius / 2;
      const dWidth = this.starRadius;
      const dHeight = this.starRadius;

      // random twinkle
      const twinkle = random(15);

      if (twinkle === 1 && this.alpha > 0) {
        this.alpha -= 0.05;
      } else if (twinkle === 2 && this.alpha < 1) {
        this.alpha += 0.05;
      }

      canvasContext.value.globalAlpha = this.alpha;
      canvasContext.value.drawImage(image, dx, dy, dWidth, dHeight);

      this.timePassed += this.speed;
    },
  };

  return star;
};

export { createStar };
