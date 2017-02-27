(ns game-of-life.core)

(defn new-grid
  "Creates a new grid width by height"
  [width height]
  (vec (repeat height (vec (repeat width false)))))

(defn assoc-cell
  "Sets a cell at coordinates x y (0 0 is top left) in grid to value"
  [grid x y value]
  (assoc-in grid [y x] value))

(defn get-cell
  "Gets a value of cell at coordinates x y (0 0 is top left)"
  [grid x y]
  (get-in grid [y x]))

(defn count-neighbours
  "Count live cells in the 8 immediate neighbours of x y"
  [grid x y]
  (let [neighbours [[-1 -1] [-1 0] [-1 1]
                    [0 -1] [0 1]
                    [1 -1] [1 0] [1 1]]]
    (->>
      neighbours
      (map
        (fn [[dx dy]] (get-cell grid (+ x dx) (+ y dy))))
      (filter true?)
      (count))))

(defn grid-map
  "Maps over the grid applying a function of arguments [x y val]"
  [grid map-fn]
  (let [w (count grid)
        h (count (get grid 0))]
    (vec
      (map
        (fn [y]
          (vec
            (map
              (fn [x] (map-fn x y (get-cell grid x y)))
              (range w))))
        (range h)))))

(defn game-step
  "For a given grid returns the next step of the game"
  [grid]
  (grid-map grid
    (fn [x y _]
      (let [n (count-neighbours grid x y)]
        (cond
          (< n 2) false
          :else true)))))
