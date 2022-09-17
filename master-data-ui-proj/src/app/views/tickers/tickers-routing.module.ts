import {NgModule} from '@angular/core';

import {RouterModule, Routes} from '@angular/router';
import {AddEditCommodityStressComponent} from './commodity-stress/add-edit-commodity-stress/add-edit-commodity-stress.component';
import {AddEditMarketPriceComponent} from './market-price/add-edit-market-price/add-edit-market-price.component';
import {CommodityStressComponent} from './commodity-stress/commodity-stress/commodity-stress.component';
import {MarketPriceComponent} from './market-price/market-price/market-price.component';

const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Tickers'
        },
        children: [
            {
                path: 'commodity-stress',
                component: CommodityStressComponent,
                data: {
                    title: 'view commodity stress'
                }
            },
            {
                path: 'add-commodity-stress',
                component: AddEditCommodityStressComponent,
                data: {
                    title: 'add commodity stress'
                }
            },
            {
                path: 'edit-commodity-stress/:id',
                component: AddEditCommodityStressComponent,
                data: {
                    title: 'edit commodity stress'
                }
            },
            {
                path: 'market-price',
                component: MarketPriceComponent,
                data: {
                    title: 'view market price'
                }
            },
            {
                path: 'add-market-price',
                component: AddEditMarketPriceComponent,
                data: {
                    title: 'view market price'
                }
            },
            {
                path: 'edit-market-price/:id',
                component: AddEditMarketPriceComponent,
                data: {
                    title: 'view market price'
                }
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TickersRoutingModule {

}
