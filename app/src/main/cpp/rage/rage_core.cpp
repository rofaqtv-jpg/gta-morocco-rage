#include "rage_core.h"
#include <android/log.h>
namespace RAGE {
    void Core::Init() {
        WorldStreaming::Init();
        EntitySystem::Init();
        __android_log_print(ANDROID_LOG_INFO,"RAGE","RAGE Engine Init - GTA Morocco");
    }
    void Core::Tick(float dt) {
        WorldStreaming::Tick(dt);
        EntitySystem::Tick(dt);
    }
    void WorldStreaming::Init() {}
    void WorldStreaming::Tick(float dt) {}
    void WorldStreaming::LoadSector(int x,int y) {}
    void EntitySystem::Init() {}
    void EntitySystem::Tick(float dt) {}
    int EntitySystem::CreateVehicle(float x,float y,float z){ return 1; }
}
