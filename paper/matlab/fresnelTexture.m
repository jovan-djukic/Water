x0 = 0 : pi / 1000 : pi / 2

n1 = 1
n2 = 3 ./ 4

eta = n1 ./ n2
y0 = (eta + (cos(x0)) .^ 2 - 1 - cos(x0)) ./ (2 .* ((eta + (cos(x0)) .^ 2 - 1 + cos(x0)))) .* (1 + ((cos(x0) .* (eta + (cos(x0)) .^ 2 - 1 + cos(x0)) -1) .^ 2) ./ ((cos(x0) .* (eta + (cos(x0)) .^ 2 - 1 - cos(x0)) +1) .^ 2))
 
x1 = linspace(0, pi / 2, 32)
y1 = (eta + (cos(x1)) .^ 2 - 1 - cos(x1)) ./ (2 .* ((eta + (cos(x1)) .^ 2 - 1 + cos(x1)))) .* (1 + ((cos(x1) .* (eta + (cos(x1)) .^ 2 - 1 + cos(x1)) -1) .^ 2) ./ ((cos(x1) .* (eta + (cos(x1)) .^ 2 - 1 - cos(x1)) +1) .^ 2))

[xa, ya] = stairs(x1, y1)

x2 = linspace(0, pi / 2, 128)
y2 = (eta + (cos(x2)) .^ 2 - 1 - cos(x2)) ./ (2 .* ((eta + (cos(x2)) .^ 2 - 1 + cos(x2)))) .* (1 + ((cos(x2) .* (eta + (cos(x2)) .^ 2 - 1 + cos(x2)) -1) .^ 2) ./ ((cos(x2) .* (eta + (cos(x2)) .^ 2 - 1 - cos(x2)) +1) .^ 2))

[xb, yb] = stairs(x2, y2)

x3 = linspace(0, pi / 2, 512)
y3 = (eta + (cos(x3)) .^ 2 - 1 - cos(x3)) ./ (2 .* ((eta + (cos(x3)) .^ 2 - 1 + cos(x3)))) .* (1 + ((cos(x3) .* (eta + (cos(x3)) .^ 2 - 1 + cos(x3)) -1) .^ 2) ./ ((cos(x3) .* (eta + (cos(x3)) .^ 2 - 1 - cos(x3)) +1) .^ 2))

[xc, yc] = stairs(x3, y3)

plot(x0, y0, 'r', x1, y1, 'g', xb, yb, 'b', xc, yc, 'k');