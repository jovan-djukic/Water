x0 = 0 : 0.0001 : 90

cosx = cos(x0 * pi / 180)

n1 = 1
n2 = 3 ./ 4

eta = n1 ./ n2
y0 = (eta + (cosx) .^ 2 - 1 - cosx) ./ (2 .* ((eta + (cosx) .^ 2 - 1 + cosx))) .* (1 + ((cosx .* (eta + (cosx) .^ 2 - 1 + cosx) -1) .^ 2) ./ ((cosx .* (eta + (cosx) .^ 2 - 1 - cosx) +1) .^ 2))
 
y1 = 1 ./ ((1 + cosx) .^ 8)

r0 = ((n1 - n2) ./ (n1 + n2)) .^ 2
y2 = r0 + (1 - r0) .* (1 - cosx) .^ 5 

plot(x0, y0, 'r', x0, y1, 'b', x0, y2, 'g')