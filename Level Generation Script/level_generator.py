import pygame
import csv
import sys
import os

pygame.init()
W, H = 900, 600
screen = pygame.display.set_mode((W, H))
pygame.display.set_caption("Rectangle Grid Editor")
clock = pygame.time.Clock()
FONT = pygame.font.Font(None, 24)

GRID_SPACING   = 50
HANDLE_SIZE    = 8
DRAG_THRESHOLD = 5  # px
CSV_FILE       = "rectangles.csv"

# None = free-size (Obstacle), tuples = fixed sizes
TYPE_SIZES = {
    "Obstacle": None,
    "Spawn":    (28, 29),
    "Shooter":  (62, 80),
    "NPC":      (45, 60),
    "Chaser":   (60, 81),
    "Coin":     (20, 20),
}

# Types with fixed size
FIXED_TYPES = [t for t, sz in TYPE_SIZES.items() if sz is not None and t != "Obstacle"]

class Block:
    def __init__(self, name, typ, rect):
        self.name = name
        self.typ  = typ
        self.rect = rect

blocks    = []
selected  = None
state     = "idle"       # idle, maybe_click, dragging, resizing, moving
start_pos = (0, 0)
preview_rect = None
move_offset  = (0, 0)

def load_csv(filename=CSV_FILE):
    """Load blocks from CSV on startup."""
    if not os.path.isfile(filename):
        return
    with open(filename, newline="") as f:
        reader = csv.reader(f)
        next(reader, None)
        for row in reader:
            if len(row) != 6:
                continue
            name, typ, xs, ys, ws, hs = row
            try:
                x, y, w, h = map(int, (xs, ys, ws, hs))
            except ValueError:
                continue
            blocks.append(Block(name, typ, pygame.Rect(x, y, w, h)))
    print(f"Loaded {len(blocks)} blocks from {filename}")

def save_csv(filename=CSV_FILE):
    """Write current blocks to CSV."""
    with open(filename, "w", newline="") as f:
        w = csv.writer(f)
        w.writerow(["name","type","x","y","width","height"])
        for b in blocks:
            r = b.rect
            w.writerow([b.name, b.typ, r.x, r.y, r.width, r.height])
    print(f"Saved {len(blocks)} blocks to {filename}")

def get_block_at(pos):
    for i, b in enumerate(blocks):
        if b.rect.collidepoint(pos):
            return i
    return None

def create_obstacle(rx, ry, w, h):
    name = input("Enter name for this Obstacle: ")
    blocks.append(Block(name, "Obstacle", pygame.Rect(rx, ry, w, h)))
    print(f"Obstacle '{name}' at ({rx},{ry}) size {w}×{h}\n")

def create_fixed(x, y):
    print("\nSelect fixed block TYPE:")
    for idx, t in enumerate(FIXED_TYPES, start=1):
        print(f"  {idx}. {t}")
    while True:
        try:
            choice = int(input("Enter number: "))
            if 1 <= choice <= len(FIXED_TYPES):
                typ = FIXED_TYPES[choice-1]
                break
        except ValueError:
            pass
        print("Invalid—try again.")
    name = input(f"Enter name for this {typ}: ")
    w, h = TYPE_SIZES[typ]
    blocks.append(Block(name, typ, pygame.Rect(x, y, w, h)))
    print(f"{typ} '{name}' at ({x},{y}) size {w}×{h}\n")

def draw_grid():
    for x in range(0, W, GRID_SPACING):
        pygame.draw.line(screen, (40,40,40), (x,0), (x,H))
    for y in range(0, H, GRID_SPACING):
        pygame.draw.line(screen, (40,40,40), (0,y), (W,y))

def draw_blocks():
    for idx, b in enumerate(blocks):
        color = (0,200,200) if idx != selected else (255,100,100)
        pygame.draw.rect(screen, color, b.rect, 2)
        if idx == selected:
            hx = b.rect.right  - HANDLE_SIZE//2
            hy = b.rect.bottom - HANDLE_SIZE//2
            pygame.draw.rect(screen, (255,100,100),
                             (hx, hy, HANDLE_SIZE, HANDLE_SIZE))

# Load previously saved blocks
load_csv()

running = True
while running:
    for ev in pygame.event.get():
        if ev.type == pygame.QUIT:
            running = False

        elif ev.type == pygame.MOUSEBUTTONDOWN and ev.button == 1:
            pos = ev.pos
            # check resize handle
            if selected is not None:
                b = blocks[selected]
                handle = pygame.Rect(
                    b.rect.right  - HANDLE_SIZE//2,
                    b.rect.bottom - HANDLE_SIZE//2,
                    HANDLE_SIZE, HANDLE_SIZE
                )
                if handle.collidepoint(pos):
                    state = "resizing"
                    continue

            hit = get_block_at(pos)
            if hit is not None:
                selected = hit
                bx, by = blocks[hit].rect.topleft
                move_offset = (pos[0]-bx, pos[1]-by)
                state = "moving"
            else:
                selected = None
                state = "maybe_click"
                start_pos = pos
                preview_rect = pygame.Rect(pos[0], pos[1], 0, 0)

        elif ev.type == pygame.MOUSEMOTION:
            if state == "maybe_click":
                dx = ev.pos[0] - start_pos[0]
                dy = ev.pos[1] - start_pos[1]
                if abs(dx) >= DRAG_THRESHOLD or abs(dy) >= DRAG_THRESHOLD:
                    state = "dragging"
            if state == "dragging":
                x0, y0 = start_pos
                x1, y1 = ev.pos
                rx, ry = min(x0,x1), min(y0,y1)
                rw, rh = abs(x1-x0), abs(y1-y0)
                preview_rect = pygame.Rect(rx, ry, rw, rh)
            elif state == "resizing" and selected is not None:
                mx, my = ev.pos
                b = blocks[selected]
                mx = max(mx, b.rect.x + 5)
                my = max(my, b.rect.y + 5)
                b.rect.width  = mx - b.rect.x
                b.rect.height = my - b.rect.y
            elif state == "moving" and selected is not None:
                mx, my = ev.pos
                ox, oy = move_offset
                b = blocks[selected]
                b.rect.x = mx - ox
                b.rect.y = my - oy

        elif ev.type == pygame.MOUSEBUTTONUP and ev.button == 1:
            if state == "dragging":
                r = preview_rect
                if r.width >= DRAG_THRESHOLD and r.height >= DRAG_THRESHOLD:
                    create_obstacle(r.x, r.y, r.width, r.height)
                preview_rect = None
            elif state == "maybe_click":
                create_fixed(ev.pos[0], ev.pos[1])
            state = "idle"

        elif ev.type == pygame.KEYDOWN:
            if ev.key == pygame.K_DELETE and selected is not None:
                del blocks[selected]
                selected = None
            elif ev.key == pygame.K_s:
                save_csv()
            elif ev.key == pygame.K_ESCAPE:
                running = False

    screen.fill((20,20,20))
    draw_grid()
    draw_blocks()
    if preview_rect and state == "dragging":
        pygame.draw.rect(screen, (200,200,50), preview_rect, 2)

    instr = ("Drag (Obstacle) | Click (Spawn/Shooter/NPC/Chaser/Coin) | "
             "Drag handle to resize | Drag block to move | Del=delete | S=save")
    txt = FONT.render(instr, True, (200,200,200))
    screen.blit(txt, (10, H-30))

    pygame.display.flip()
    clock.tick(60)

pygame.quit()
sys.exit()
