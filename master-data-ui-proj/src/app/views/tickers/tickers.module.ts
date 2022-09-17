import {NgModule} from '@angular/core';
import {AddEditCommodityStressComponent} from './commodity-stress/add-edit-commodity-stress/add-edit-commodity-stress.component';
import {AddEditMarketPriceComponent} from './market-price/add-edit-market-price/add-edit-market-price.component';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {GlobalModule} from '../global/global.module';
import {PipeModule} from '../pipes/pipe.module';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {TickersRoutingModule} from './tickers-routing.module';
import {CommodityStressComponent} from './commodity-stress/commodity-stress/commodity-stress.component';
import {MarketPriceComponent} from './market-price/market-price/market-price.component';
import { MatSortModule } from '@angular/material';

@NgModule({

    declarations: [
        AddEditCommodityStressComponent,
        AddEditMarketPriceComponent,
        CommodityStressComponent,
        MarketPriceComponent
    ],
    imports: [
        CommonModule,
        TickersRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
    ]
})
export class tickersModule {
}
