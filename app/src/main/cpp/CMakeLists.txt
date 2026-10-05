cmake_minimum_required(VERSION 3.22.1)
project("gta_morocco")

add_library(gta_morocco SHARED
    game_main.cpp
    rage/rage_core.cpp
    rage/world_streaming.cpp
    rage/entity_system.cpp
    physics/physics_world.cpp
    scripting/lua_bridge.cpp
)

find_library(log-lib log)
target_link_libraries(gta_morocco ${log-lib} android)
