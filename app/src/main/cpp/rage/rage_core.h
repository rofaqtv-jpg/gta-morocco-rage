#pragma once
namespace RAGE {
    class Core {
    public:
        static void Init();
        static void Tick(float dt);
    };
    class WorldStreaming {
    public:
        static void Init();
        static void Tick(float dt);
        static void LoadSector(int x, int y);
    };
    struct Entity { int id; float x,y,z; };
    class EntitySystem {
    public:
        static void Init();
        static void Tick(float dt);
        static int CreateVehicle(float x,float y,float z);
    };
}
