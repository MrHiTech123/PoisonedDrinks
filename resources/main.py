from mcresources import ResourceManager
from alcs_funcs import *


CROPS: Dict[str, Crop] = {
    'hemlock': Crop('default', 5, 'potassium', 3, 30, 100, 400, 25, 100, None, None)
}
POISONED_ALCOHOLS = ['poisoned_' + alcohol for alcohol in ALCOHOLS]
POISONED_ALCOHOLS.extend(['poisoned_aged_' + alcohol for alcohol in ALCOHOLS])
POISONED_ALCOHOLS.append('poisoned_water')


rm = ResourceManager('poisoned_drinks')

def generate_crops():
    print('Generating general crop stuff...')
    for crop, crop_data in CROPS.items():
        name = f'poisoned_drinks:food/{crop}'
        if crop_data.type == 'default':
            block = rm.blockstate(('crop', crop), variants=dict((f'age={i}', {'model': f'poisoned_drinks:block/crop/{crop}_age_{i}'}) for i in range(crop_data.stages)))
            block.with_lang(lang(crop))
            for i in range(crop_data.stages):
                rm.block_model(('crop', f'{crop}_age_{i}'), textures={'crop': f'poisoned_drinks:block/crop/{crop}_{i}'}, parent='block/crop')
                
            block.with_block_loot({
                'name': name,
                'conditions': loot_tables.block_state_property(f'poisoned_drinks:crop/{crop}[age={crop_data.stages - 1}]'),
                'functions': crop_yield(0, (6, 10))
            }, {
                'name': f'poisoned_drinks:seeds/{crop}'
            }
            )
        
        block = rm.blockstate(('dead_crop', crop), variants={
            'mature=true': {'model': f'poisoned_drinks:block/dead_crop/{crop}'},
            'mature=false': {'model': f'poisoned_drinks:block/dead_crop/{crop}_young'}
        })
        
        block.with_lang(lang(f'dead {crop}'))
        rm.block_model(('dead_crop', f'{crop}_young'), textures={'crop': f'poisoned_drinks:block/crop/{crop}_dead_young'}, parent='block/crop')
        rm.block_model(('dead_crop', f'{crop}'), textures={'crop': f'poisoned_drinks:block/crop/{crop}_dead'}, parent='block/crop')
        
        block.with_block_loot(loot_tables.alternatives({
            'name': f'poisoned_drinks:seeds/{crop}',
            'conditions': loot_tables.block_state_property(f'poisoned_drinks:dead_crop/{crop}[mature=true]'),
            'functions': loot_tables.set_count(1, 3)
        }, {
            'name': f'poisoned_drinks:seeds/{crop}',
            'conditions': loot_tables.block_state_property(f'poisoned_drinks:dead_crop/{crop}[mature=false]')
        }))
        
        block = rm.block(('wild_crop', crop)).with_lang(lang(f'Wild {crop}'))
        block.with_block_model(textures={'crop': f'poisoned_drinks:block/crop/{crop}_wild'}, parent='tfc:block/wild_crop/crop')
        rm.item_model(('wild_crop', crop), parent=f'poisoned_drinks:block/wild_crop/{crop}', no_textures=True)
        
        block.with_blockstate(variants={'mature=true': {'model': f'poisoned_drinks:block/wild_crop/{crop}'}, 'mature=false': {'model': f'poisoned_drinks:block/dead_crop/{crop}'}}, use_default_model=False)
        block.with_block_loot({
            'name': name,
            'functions': loot_tables.set_count(1, 3),
            'conditions': [loot_tables.block_state_property(f'poisoned_drinks:wild_crop/{crop}[mature=true]')]
        },{
            'name': f'poisoned_drinks:seeds/{crop}'
        })
        
        rm.item_model(('seeds', crop)).with_lang(lang('%s seeds', crop)).with_tag('tfc:seeds')

def generate_food():
    print('Generating food items...')
    food_item(rm, ('hemlock'), 'poisoned_drinks:food/hemlock', Category.vegetable, 4, 2, 0, decay=0.7, veg=1)
    food_item(rm, ('cooked_hemlock'), 'poisoned_drinks:food/cooked_hemlock', Category.vegetable, 6, 2, 0, decay=0.7, veg=1.5)
    

def generate_block_models():
    print('\tGenerating block models...')
    for alcohol in POISONED_ALCOHOLS:
        water_based_fluid(rm, alcohol)

def generate_item_models():
    print('\tGenerating item models...')
    rm.item_model(('food', 'hemlock'), 'poisoned_drinks:item/food/hemlock').with_lang('Hemlock')
    rm.item_model(('food', 'cooked_hemlock'), 'poisoned_drinks:item/food/cooked_hemlock').with_lang('Cooked Hemlock')
    rm.item_model(('powder', 'hemlock'), 'poisoned_drinks:item/powder/hemlock').with_lang('Hemlock Powder')
    
    
def generate_models():
    print('Generating models...')
    generate_block_models()
    generate_item_models()

def generate_drinks():
    print('Generating drinks...')
    drinkable(rm, ('poison'), '#poisoned_drinks:poisons', effects=[{'type': 'minecraft:nausea', 'duration': (60 * 20)}], allow_full=True)
    drinkable(rm, ('industrial_fluids'), '#poisoned_drinks:industrial_fluids', effects=[{'type': 'minecraft:nausea', 'duration': (60 * 20)}, {'type': 'minecraft:wither', 'duration': (120 * 20), 'amplifier': 15}], allow_full=True)

def generate_heats():
    print('Generating heats...')
    item_heat(rm, ('food', 'hemlock'), 'poisoned_drinks:food/hemlock', 1.0)

def generate_misc_lang():
    print('Generating misc lang...')
    for alcohol in ALCOHOLS:
        rm.lang(f'fluid.poisoned_drinks.poisoned_{alcohol}', lang(alcohol))
        rm.lang(f'item.poisoned_drinks.bucket.poisoned_{alcohol}', lang(f'Poisoned {alcohol} bucket'))
        rm.lang(f'block.poisoned_drinks.fluid.poisoned_{alcohol}', lang(alcohol))
        rm.lang(f'fluid.poisoned_drinks.poisoned_aged_{alcohol}', lang(f'Aged {alcohol}'))
        rm.lang(f'item.poisoned_drinks.bucket.poisoned_aged_{alcohol}', lang(f'Poisoned Aged {alcohol} bucket'))
        rm.lang(f'block.poisoned_drinks.fluid.poisoned_aged_{alcohol}', lang(f'Aged {alcohol}'))
    
    rm.lang('fluid.poisoned_drinks.poisoned_water', 'Water')
    rm.lang('item.poisoned_drinks.bucket.poisoned_water', 'Poisoned Water Bucket')
    rm.lang('block.poisoned_drinks.poisoned_water', 'Water')
    rm.lang('death.attack.vomiting', "%1$s vomited to death")
    

def generate_instant_barrel_recipes():
    print('\tGenerating instant barrel recipes...')
    for alcohol in ALCOHOLS:
        barrel_instant_recipe(rm, ('poison', alcohol), 'poisoned_drinks:powder/hemlock', f'400 tfc:{alcohol}', None, f'400 poisoned_drinks:poisoned_{alcohol}')
        barrel_instant_recipe(rm, ('poison', f'aged_{alcohol}'), 'poisoned_drinks:powder/hemlock', f'400 tfcagedalcohol:aged_{alcohol}', None, f'400 poisoned_drinks:poisoned_aged_{alcohol}')
    barrel_instant_recipe(rm, ('poison', 'water'), 'poisoned_drinks:powder/hemlock', '400 minecraft:water', None, '400 poisoned_drinks:poisoned_water')
    
def generate_heat_recipes():
    print('\tGenerating heat recipes...')
    heat_recipe(rm, ('food', 'cooked_hemlock'), 'poisoned_drinks:food/hemlock', 200, 'poisoned_drinks:food/cooked_hemlock')

def generate_quern_recipes():
    print('\tGenerating quern recipes...')
    quern_recipe(rm, ('food', 'cooked_hemlock'), 'poisoned_drinks:food/cooked_hemlock', 'poisoned_drinks:powder/hemlock')

def generate_recipes():
    print('Generating recipes...')
    generate_instant_barrel_recipes()
    generate_heat_recipes()
    generate_quern_recipes()

def generate_fluid_tags():
    print('\tGenerating fluid tags...')
    rm.fluid_tag(('poisons'), *POISONED_ALCOHOLS)
    rm.fluid_tag('industrial_fluids', 'tfc:lye', 'tfc:limewater')
    rm.fluid_tag('tfc:drinkables', '#poisoned_drinks:poisons', '#poisoned_drinks:industrial_fluids')
    
    
def generate_tags():
    print('Generating tags...')
    generate_fluid_tags()

def generate_worldgen():
    print('Generating worldgen...')
    for crop, crop_data in CROPS.items():
        name_parts = ('crop', 'wild_crop', crop)
        name = f'poisoned_drinks:wild_crop/{crop}'
        heightmap: Heightmap = 'world_surface_wg'
        replaceable = decorate_replaceable()
        
        feature = 'simple_block', {'to_place': simple_state_provider(name)}
        
        res = utils.resource_location(rm.domain, name_parts)
        patch_feature = res.join() + '_patch'
        singular_feature = utils.resource_location(rm.domain, name_parts)
        
        rm.placed_feature_tag('tfc:feature/crops', patch_feature)
        
        rm.configured_feature(patch_feature, 'minecraft:random_patch', {'tries': 6, 'xz_spread': 5, 'y_spread': 1, 'feature': singular_feature.join()})
        rm.configured_feature(singular_feature, *feature)
        rm.placed_feature(patch_feature, patch_feature, decorate_chance(80), decorate_square(), decorate_climate(crop_data.min_temp, crop_data.max_temp, crop_data.min_rain, crop_data.max_rain, min_forest=crop_data.min_forest, max_forest=crop_data.max_forest))
        rm.placed_feature(singular_feature, singular_feature, decorate_heightmap(heightmap), replaceable, decorate_would_survive(name))
        
        
        

def main():
    generate_crops()
    generate_food()
    generate_heats()
    generate_models()
    generate_drinks()
    generate_misc_lang()
    generate_recipes()
    generate_tags()
    generate_worldgen()
    
    rm.flush()

main()